/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2017-2017 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2017 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.notifd.pd;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import org.apache.commons.lang.StringUtils;
import org.opennms.core.spring.BeanUtils;
import org.opennms.netmgt.config.NotificationManager;
import org.opennms.netmgt.config.notifd.AutoAcknowledge;
import org.opennms.netmgt.dao.api.EventDao;
import org.opennms.netmgt.model.OnmsAlarm;
import org.opennms.netmgt.model.OnmsEvent;
import org.opennms.netmgt.model.OnmsEventParameter;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.OnmsServiceType;
import org.opennms.netmgt.model.OnmsSeverity;
import org.opennms.netmgt.model.notifd.Argument;
import org.opennms.netmgt.model.notifd.NotificationStrategy;
import org.opennms.netmgt.notifd.pd.client.api.PDClient;
import org.opennms.netmgt.notifd.pd.client.api.PDClientFactory;
import org.opennms.netmgt.notifd.pd.client.api.PDEvent;
import org.opennms.netmgt.notifd.pd.client.api.PDEventAction;
import org.opennms.netmgt.notifd.pd.client.api.PDEventPayload;
import org.opennms.netmgt.notifd.pd.client.api.PDEventSeverity;
import org.opennms.netmgt.notifd.pd.client.impl.DefaultPDClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Pager Duty Integration.
 *
 * NICE TO HAVE:
 *  * Poll for ACKs
 *  * Scheduled outages
 */
public class PagerDutyNotificationStrategy implements NotificationStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(PagerDutyNotificationStrategy.class);

    private final Supplier<EventDao> eventDao = Suppliers.memoize(() -> BeanUtils.getBean("daoContext", "eventDao", EventDao.class));
    private final Supplier<TransactionTemplate> transTemplate = Suppliers.memoize(() -> BeanUtils.getBean("daoContext", "transactionTemplate", TransactionTemplate.class));

    private PDClientFactory clientFactory = DefaultPDClientFactory.INSTANCE;

    void send(PagerDutyNotice notice) {
        try(PDClient client = clientFactory.getClient()) {
            client.sendEvent(noticeToEvent(notice));
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public static PDEvent noticeToEvent(PagerDutyNotice notice) {
        final PDEvent e = new PDEvent();
        e.setClient("OpenNMS");
        e.setClientUrl(String.format("http://127.0.0.1:8980/opennms/notification/detail.jsp?notice=%d", notice.getNoticeId()));
        e.setRoutingKey(notice.getRoutingKey());
        // De-duplicate by notice id
        e.setDedupKey(Integer.toString(notice.getNoticeId()));

        // TODO: Add links
        // e.getLinks();

        // NICE TO HAVE: Add a graph of the metric if it's a threshold
        // e.getImages()

        // Are we resolving the notice?
        if (isNoticeResolved(notice)) {
            e.setEventAction(PDEventAction.RESOLVE);
        } else {
            // FIXME: We need to support ACKs too
            e.setEventAction(PDEventAction.TRIGGER);
        }

        final PDEventPayload payload = new PDEventPayload();
        e.setPayload(payload);
        // Subject -> Summary
        payload.setSummary(notice.getSubject());
        // Severity -> Severity
        payload.setSeverity(PDEventSeverity.fromOnmsSeverity(
                OnmsSeverity.get(notice.getEvent().getEventSeverity())));

        // Service Name
        final OnmsServiceType serviceType = notice.getEvent().getServiceType();
        final String serviceName = serviceType != null ? serviceType.getName() : null;

        // Reduction Key
        final OnmsAlarm alarm = notice.getEvent().getAlarm();
        final String reductionKey = alarm != null ? alarm.getReductionKey() : null;

        // Node identifiers
        Integer nodeId = null;
        String foreignSource = null;
        String foreignId = null;
        final OnmsNode node = notice.getEvent().getNode();
        if (node != null) {
            nodeId = node.getId();
            foreignSource = node.getForeignId();
            foreignId = node.getForeignId();
        }
        // Use the information we've gathered to set the component
        payload.setComponent(String.format("%s - %s - %s:%s(%d)",
                serviceName,
                reductionKey,
                foreignSource,
                foreignId,
                nodeId));

        if (node != null) {
            // Use the label as the source
            payload.setSource(node.getLabel());
            // And the foreign source as the group
            payload.setGroup(node.getForeignSource());
        }
        // TODO: What if we don't have a node?

        // Add all of the event parameters as custom details
        payload.getCustomDetails().putAll(eparmsToMap(
                notice.getEvent().getEventParameters()));
        return e;
    }

    protected static boolean isNoticeResolved(PagerDutyNotice notice) {
        // FIXME: We can't rely on this constant, since it's configurable
        final String subject = notice.getSubject();
        return subject != null &&
                subject.startsWith(AutoAcknowledge.DEFAULT_RESOLUTION_PREFIX);
    }

    protected static Map<String, Object> eparmsToMap(List<OnmsEventParameter> eparms) {
        final Map<String, Object> map = new LinkedHashMap<>();
        if (eparms == null) {
            return map;
        }
        eparms.stream().forEach(p -> {
            map.put(p.getName(), p.getValue());
        });
        return map;
    }

    @Override
    public int send(List<Argument> arguments) {
        Integer eventId = null;
        Integer noticeId = null;
        String subject = null;
        String body = null;

        LOG.debug("Called with arguments: {}", arguments);
        for (Argument arg : arguments) {
            if ("eventID".equalsIgnoreCase(arg.getSwitch())) {
                if (!StringUtils.isBlank(arg.getValue())) {
                    eventId = Integer.parseInt(arg.getValue());
                }
            } else if ("noticeid".equalsIgnoreCase(arg.getSwitch())) {
                if (!StringUtils.isBlank(arg.getValue())) {
                    noticeId = Integer.parseInt(arg.getValue());
                }
            } else if (NotificationManager.PARAM_SUBJECT.equals(arg.getSwitch())) {
                subject = arg.getValue();
            } else if (NotificationManager.PARAM_TEXT_MSG.equals(arg.getSwitch())) {
                body = arg.getValue();
            }
        }

        // Make sure we have the arguments we need.
        if (noticeId == null) {
            throw new IllegalStateException("Missing required parameter: noticeid");
        } else if (eventId == null) {
            throw new IllegalStateException("Missing required parameter: eventID");
        }

        // Lookup the associated event
        final Integer effectiveEventId = eventId;
        final OnmsEvent event = transTemplate.get().execute(status -> {
            final OnmsEvent eventFromDao = eventDao.get().get(effectiveEventId);
            if (eventFromDao == null) {
                return null;
            }
            // Trigger lazy-loading of any properties we may need laters
            eventFromDao.getEventParameters();
            final OnmsNode nodeFromDao = eventFromDao.getNode();
            if (nodeFromDao != null) {
                nodeFromDao.getLabel();
                nodeFromDao.getCategories();
            }
            eventFromDao.getAlarm();
            return eventFromDao;
        });
        if (event == null) {
            throw new IllegalStateException("No event found with id: " + eventId);
        }

        final PagerDutyNotice notice = new PagerDutyNotice(event, noticeId, subject, body);
        // FIXME: Make this configurable
        notice.setRoutingKey("YOU-ROUTING-KEY-HERE");
        send(notice);
        return 0;
    }

    public void setClientFactory(PDClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
