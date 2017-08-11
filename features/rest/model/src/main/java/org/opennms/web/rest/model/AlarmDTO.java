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

package org.opennms.web.rest.model;

import org.opennms.netmgt.model.OnmsMemo;
import org.opennms.netmgt.model.OnmsReductionKeyMemo;
import org.opennms.netmgt.model.OnmsServiceType;
import org.opennms.netmgt.model.OnmsSeverity;
import org.opennms.netmgt.model.TroubleTicketState;

import java.net.InetAddress;
import java.util.Date;
import java.util.Map;

public class AlarmDTO {

    private Integer id;

    private String uei;

    // From OnmsMonitoringSystem.distPoller
    private String location;

    private InetAddress ipAddr;

    private OnmsServiceType serviceType;

    private String reductionKey;

    private Integer alarmType;

    private Integer ifIndex;

    private Integer counter;

    private OnmsSeverity severity = OnmsSeverity.INDETERMINATE;

    private Date firstEventTime;

    private Date lastEventTime;

    private Date firstAutomationTime;

    private Date lastAutomationTime;

    private String description;

    private String logMsg;

    private String operInstruct;

    private String tTicketId;

    private TroubleTicketState tTicketState;

    private String mouseOverText;

    private Date suppressedUntil;

    private String suppressedUser;

    private Date suppressedTime;

    private String alarmAckUser;

    private Date alarmAckTime;

    private String clearKey;

    private EventDTO lastEvent;

    private String eventParms;

    private String managedObjectInstance;

    private String managedObjectType;

    private String applicationDN;

    private String ossPrimaryKey;

    private String x733AlarmType;

    private String qosAlarmState;

    private int x733ProbableCause;

    private Map<String, String> details;

    private OnmsMemo stickyMemo;

    private OnmsReductionKeyMemo reductionKeyMemo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUei() {
        return uei;
    }

    public void setUei(String uei) {
        this.uei = uei;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public InetAddress getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(InetAddress ipAddr) {
        this.ipAddr = ipAddr;
    }

    public OnmsServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(OnmsServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getReductionKey() {
        return reductionKey;
    }

    public void setReductionKey(String reductionKey) {
        this.reductionKey = reductionKey;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Integer getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(Integer ifIndex) {
        this.ifIndex = ifIndex;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public OnmsSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(OnmsSeverity severity) {
        this.severity = severity;
    }

    public Date getFirstEventTime() {
        return firstEventTime;
    }

    public void setFirstEventTime(Date firstEventTime) {
        this.firstEventTime = firstEventTime;
    }

    public Date getLastEventTime() {
        return lastEventTime;
    }

    public void setLastEventTime(Date lastEventTime) {
        this.lastEventTime = lastEventTime;
    }

    public Date getFirstAutomationTime() {
        return firstAutomationTime;
    }

    public void setFirstAutomationTime(Date firstAutomationTime) {
        this.firstAutomationTime = firstAutomationTime;
    }

    public Date getLastAutomationTime() {
        return lastAutomationTime;
    }

    public void setLastAutomationTime(Date lastAutomationTime) {
        this.lastAutomationTime = lastAutomationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getOperInstruct() {
        return operInstruct;
    }

    public void setOperInstruct(String operInstruct) {
        this.operInstruct = operInstruct;
    }

    public String gettTicketId() {
        return tTicketId;
    }

    public void settTicketId(String tTicketId) {
        this.tTicketId = tTicketId;
    }

    public TroubleTicketState gettTicketState() {
        return tTicketState;
    }

    public void settTicketState(TroubleTicketState tTicketState) {
        this.tTicketState = tTicketState;
    }

    public String getMouseOverText() {
        return mouseOverText;
    }

    public void setMouseOverText(String mouseOverText) {
        this.mouseOverText = mouseOverText;
    }

    public Date getSuppressedUntil() {
        return suppressedUntil;
    }

    public void setSuppressedUntil(Date suppressedUntil) {
        this.suppressedUntil = suppressedUntil;
    }

    public String getSuppressedUser() {
        return suppressedUser;
    }

    public void setSuppressedUser(String suppressedUser) {
        this.suppressedUser = suppressedUser;
    }

    public Date getSuppressedTime() {
        return suppressedTime;
    }

    public void setSuppressedTime(Date suppressedTime) {
        this.suppressedTime = suppressedTime;
    }

    public String getAlarmAckUser() {
        return alarmAckUser;
    }

    public void setAlarmAckUser(String alarmAckUser) {
        this.alarmAckUser = alarmAckUser;
    }

    public Date getAlarmAckTime() {
        return alarmAckTime;
    }

    public void setAlarmAckTime(Date alarmAckTime) {
        this.alarmAckTime = alarmAckTime;
    }

    public String getClearKey() {
        return clearKey;
    }

    public void setClearKey(String clearKey) {
        this.clearKey = clearKey;
    }

    public EventDTO getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(EventDTO lastEvent) {
        this.lastEvent = lastEvent;
    }

    public String getEventParms() {
        return eventParms;
    }

    public void setEventParms(String eventParms) {
        this.eventParms = eventParms;
    }

    public String getManagedObjectInstance() {
        return managedObjectInstance;
    }

    public void setManagedObjectInstance(String managedObjectInstance) {
        this.managedObjectInstance = managedObjectInstance;
    }

    public String getManagedObjectType() {
        return managedObjectType;
    }

    public void setManagedObjectType(String managedObjectType) {
        this.managedObjectType = managedObjectType;
    }

    public String getApplicationDN() {
        return applicationDN;
    }

    public void setApplicationDN(String applicationDN) {
        this.applicationDN = applicationDN;
    }

    public String getOssPrimaryKey() {
        return ossPrimaryKey;
    }

    public void setOssPrimaryKey(String ossPrimaryKey) {
        this.ossPrimaryKey = ossPrimaryKey;
    }

    public String getX733AlarmType() {
        return x733AlarmType;
    }

    public void setX733AlarmType(String x733AlarmType) {
        this.x733AlarmType = x733AlarmType;
    }

    public String getQosAlarmState() {
        return qosAlarmState;
    }

    public void setQosAlarmState(String qosAlarmState) {
        this.qosAlarmState = qosAlarmState;
    }

    public int getX733ProbableCause() {
        return x733ProbableCause;
    }

    public void setX733ProbableCause(int x733ProbableCause) {
        this.x733ProbableCause = x733ProbableCause;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public OnmsMemo getStickyMemo() {
        return stickyMemo;
    }

    public void setStickyMemo(OnmsMemo stickyMemo) {
        this.stickyMemo = stickyMemo;
    }

    public OnmsReductionKeyMemo getReductionKeyMemo() {
        return reductionKeyMemo;
    }

    public void setReductionKeyMemo(OnmsReductionKeyMemo reductionKeyMemo) {
        this.reductionKeyMemo = reductionKeyMemo;
    }
}
