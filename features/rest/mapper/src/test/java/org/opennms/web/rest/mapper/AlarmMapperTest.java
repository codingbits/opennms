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

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.opennms.netmgt.config.api.EventConfDao;
import org.opennms.web.rest.model.*;
import org.junit.Test;
import org.opennms.netmgt.model.OnmsAlarm;
import org.opennms.netmgt.model.OnmsEvent;
import org.opennms.netmgt.model.OnmsMonitoringSystem;
import org.opennms.web.rest.model.AlarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlarmMapperTest {

    private AlarmMapper mapper;

    @Before
    public void setUp() {
        mapper = Mappers.getMapper(AlarmMapper.class);
    }

    @Test
    public void canMapAlarm() {
        EventConfDao eventConfDao = mock(EventConfDao.class);
        when(eventConfDao.getEventLabel("some-uei")).thenReturn("some-label");
        mapper.setEventConfDao(eventConfDao);

        OnmsAlarm alarm = new OnmsAlarm();
        alarm.setId(1);

        OnmsMonitoringSystem distPoller = new OnmsMonitoringSystem();
        distPoller.setLocation("some-location");
        alarm.setDistPoller(distPoller);

        OnmsEvent event = new OnmsEvent();
        event.setId(1);
        event.setEventUei("some-uei");
        alarm.setLastEvent(event);

        AlarmDTO alarmDTO = mapper.alarmToAlarmDTO(alarm);
        assertThat(alarmDTO.getId(), equalTo(1));
        assertThat(alarmDTO.getLocation(), equalTo("some-location"));
        assertThat(alarmDTO.getLastEvent().getId(), equalTo(1));
        assertThat(alarmDTO.getLastEvent().getLabel(), equalTo("some-label"));
    }
}
