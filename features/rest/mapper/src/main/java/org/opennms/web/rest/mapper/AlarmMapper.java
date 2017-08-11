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

package org.opennms.web.rest.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.opennms.netmgt.config.api.EventConfDao;
import org.opennms.netmgt.model.OnmsAlarm;
import org.opennms.netmgt.model.OnmsEvent;
import org.opennms.web.rest.model.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {})
public abstract class AlarmMapper {

    @Autowired
    private EventConfDao eventConfDao;

    public static final AlarmMapper INSTANCE = Mappers.getMapper(AlarmMapper.class);

    @Mappings({
            @Mapping(source = "distPoller.location", target = "location"),
    })
    public abstract AlarmDTO alarmToAlarmDTO(OnmsAlarm alarm);

    @Mappings({
            @Mapping(source = "distPoller.location", target = "location"),
    })
    public abstract EventDTO eventToEventDTO(OnmsEvent event);

    @AfterMapping
    protected void fillEvent(OnmsEvent event, @MappingTarget EventDTO eventDTO) {
        eventDTO.setLabel(eventConfDao.getEventLabel(eventDTO.getEventUei()));
    }

    @AfterMapping
    protected void fillAlarm(OnmsAlarm alarm, @MappingTarget AlarmDTO alarmDTO) {
        // pass
    }

    public void setEventConfDao(EventConfDao eventConfDao) {
        this.eventConfDao = eventConfDao;
    }
}
