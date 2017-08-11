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

import org.opennms.netmgt.model.OnmsAlarm;
import org.opennms.netmgt.model.OnmsMonitoringSystem;
import org.opennms.netmgt.model.OnmsNotification;
import org.opennms.netmgt.model.OnmsOutage;
import org.opennms.netmgt.model.OnmsServiceType;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EventDTO {

    private Integer id;

    private String eventUei;

    private Date eventTime;

    private String eventHost;

    private String eventSource;

    private InetAddress ipAddr;

    private String location;

    private String eventSnmpHost;

    private OnmsServiceType serviceType;

    private String eventSnmp;

    private String eventParms;

    private Date eventCreateTime;

    private String eventDescr;

    private String eventLogGroup;

    private String eventLogMsg;

    private Integer eventSeverity;

    private Integer ifIndex;

    private String eventPathOutage;

    private String eventCorrelation;

    private Integer eventSuppressedCount;

    private String eventOperInstruct;

    private String eventAutoAction;

    private String eventOperAction;

    private String eventOperActionMenuText;

    private String eventNotification;

    private String eventTTicket;

    private Integer eventTTicketState;

    private String eventForward;

    private String eventMouseOverText;

    private String eventLog;

    private String eventDisplay;

    private String eventAckUser;

    private Date eventAckTime;

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventUei() {
        return eventUei;
    }

    public void setEventUei(String eventUei) {
        this.eventUei = eventUei;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventHost() {
        return eventHost;
    }

    public void setEventHost(String eventHost) {
        this.eventHost = eventHost;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

    public InetAddress getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(InetAddress ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventSnmpHost() {
        return eventSnmpHost;
    }

    public void setEventSnmpHost(String eventSnmpHost) {
        this.eventSnmpHost = eventSnmpHost;
    }

    public OnmsServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(OnmsServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getEventSnmp() {
        return eventSnmp;
    }

    public void setEventSnmp(String eventSnmp) {
        this.eventSnmp = eventSnmp;
    }

    public String getEventParms() {
        return eventParms;
    }

    public void setEventParms(String eventParms) {
        this.eventParms = eventParms;
    }

    public Date getEventCreateTime() {
        return eventCreateTime;
    }

    public void setEventCreateTime(Date eventCreateTime) {
        this.eventCreateTime = eventCreateTime;
    }

    public String getEventDescr() {
        return eventDescr;
    }

    public void setEventDescr(String eventDescr) {
        this.eventDescr = eventDescr;
    }

    public String getEventLogGroup() {
        return eventLogGroup;
    }

    public void setEventLogGroup(String eventLogGroup) {
        this.eventLogGroup = eventLogGroup;
    }

    public String getEventLogMsg() {
        return eventLogMsg;
    }

    public void setEventLogMsg(String eventLogMsg) {
        this.eventLogMsg = eventLogMsg;
    }

    public Integer getEventSeverity() {
        return eventSeverity;
    }

    public void setEventSeverity(Integer eventSeverity) {
        this.eventSeverity = eventSeverity;
    }

    public Integer getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(Integer ifIndex) {
        this.ifIndex = ifIndex;
    }

    public String getEventPathOutage() {
        return eventPathOutage;
    }

    public void setEventPathOutage(String eventPathOutage) {
        this.eventPathOutage = eventPathOutage;
    }

    public String getEventCorrelation() {
        return eventCorrelation;
    }

    public void setEventCorrelation(String eventCorrelation) {
        this.eventCorrelation = eventCorrelation;
    }

    public Integer getEventSuppressedCount() {
        return eventSuppressedCount;
    }

    public void setEventSuppressedCount(Integer eventSuppressedCount) {
        this.eventSuppressedCount = eventSuppressedCount;
    }

    public String getEventOperInstruct() {
        return eventOperInstruct;
    }

    public void setEventOperInstruct(String eventOperInstruct) {
        this.eventOperInstruct = eventOperInstruct;
    }

    public String getEventAutoAction() {
        return eventAutoAction;
    }

    public void setEventAutoAction(String eventAutoAction) {
        this.eventAutoAction = eventAutoAction;
    }

    public String getEventOperAction() {
        return eventOperAction;
    }

    public void setEventOperAction(String eventOperAction) {
        this.eventOperAction = eventOperAction;
    }

    public String getEventOperActionMenuText() {
        return eventOperActionMenuText;
    }

    public void setEventOperActionMenuText(String eventOperActionMenuText) {
        this.eventOperActionMenuText = eventOperActionMenuText;
    }

    public String getEventNotification() {
        return eventNotification;
    }

    public void setEventNotification(String eventNotification) {
        this.eventNotification = eventNotification;
    }

    public String getEventTTicket() {
        return eventTTicket;
    }

    public void setEventTTicket(String eventTTicket) {
        this.eventTTicket = eventTTicket;
    }

    public Integer getEventTTicketState() {
        return eventTTicketState;
    }

    public void setEventTTicketState(Integer eventTTicketState) {
        this.eventTTicketState = eventTTicketState;
    }

    public String getEventForward() {
        return eventForward;
    }

    public void setEventForward(String eventForward) {
        this.eventForward = eventForward;
    }

    public String getEventMouseOverText() {
        return eventMouseOverText;
    }

    public void setEventMouseOverText(String eventMouseOverText) {
        this.eventMouseOverText = eventMouseOverText;
    }

    public String getEventLog() {
        return eventLog;
    }

    public void setEventLog(String eventLog) {
        this.eventLog = eventLog;
    }

    public String getEventDisplay() {
        return eventDisplay;
    }

    public void setEventDisplay(String eventDisplay) {
        this.eventDisplay = eventDisplay;
    }

    public String getEventAckUser() {
        return eventAckUser;
    }

    public void setEventAckUser(String eventAckUser) {
        this.eventAckUser = eventAckUser;
    }

    public Date getEventAckTime() {
        return eventAckTime;
    }

    public void setEventAckTime(Date eventAckTime) {
        this.eventAckTime = eventAckTime;
    }
}
