/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opennms.core.spring.BeanUtils;
import org.opennms.core.test.OpenNMSJUnit4ClassRunner;
import org.opennms.core.test.db.annotations.JUnitTemporaryDatabase;
import org.opennms.netmgt.dao.api.NodeDao;
import org.opennms.netmgt.dao.api.SnmpInterfaceDao;
import org.opennms.netmgt.model.OnmsSnmpInterface;
import org.opennms.test.JUnitConfigurationEnvironment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * UpsertTest
 *
 * @author brozow
 */
@RunWith(OpenNMSJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:/META-INF/opennms/applicationContext-soa.xml",
        "classpath:/META-INF/opennms/applicationContext-dao.xml",
        "classpath*:/META-INF/opennms/component-dao.xml",
        "classpath:/META-INF/opennms/applicationContext-databasePopulator.xml",
        "classpath:/upsertTest-context.xml",
        "classpath:/META-INF/opennms/applicationContext-commonConfigs.xml",
        "classpath:/META-INF/opennms/applicationContext-minimal-conf.xml"
})
@JUnitConfigurationEnvironment
@JUnitTemporaryDatabase
public class UpsertIT implements InitializingBean {
    
    @Autowired
    UpsertService m_upsertService;

    @Autowired
    NodeDao m_nodeDao;
    
    @Autowired
    SnmpInterfaceDao m_snmpIfaceDao;
    
    @Autowired
    JdbcTemplate m_jdbcTemplate;
    
    @Autowired
    DatabasePopulator m_populator;

    @Autowired
    TransactionTemplate m_transactionTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanUtils.assertAutowiring(this);
    }

    @BeforeTransaction
    public void setUpDatabase() {
        m_populator.populateDatabase();
        m_transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                m_jdbcTemplate.execute("insert into accesslocks values ('SNMPINTERFACE_ACCESS')");
            }
        });
    }

    @Test
    @JUnitTemporaryDatabase
    @Transactional
    public void testInsert() {
        String newIfName = "newIf0";
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 1, "atm0"));
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 2, "eth0"));
        assertEquals(0, countIfs(m_populator.getNode1().getId(), 1001, newIfName));

        // add non-existent snmpiface
        OnmsSnmpInterface snmpIface = new OnmsSnmpInterface();
        snmpIface.setNode(m_populator.getNode1());
        snmpIface.setIfIndex(1001);
        snmpIface.setIfName(newIfName);
        
        m_upsertService.upsert(m_populator.getNode1().getId() /* nodeid */, snmpIface, 0);
        
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 1, "atm0"));
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 2, "eth0"));
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 1001, newIfName));
    }
    
    private int countIfs(int nodeId, int ifIndex, String ifName) {
        return m_jdbcTemplate.queryForObject("select count(*) from snmpInterface where nodeid=? and snmpifindex=? and snmpifname=?", new Object[] { nodeId, ifIndex, ifName }, Integer.class);
    }
    
    @Test
    @JUnitTemporaryDatabase
    @Transactional
    public void testUpdate() {
        String oldIfName = "eth0";
        String newIfName = "newIf0";
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 2, oldIfName));
        assertEquals(0, countIfs(m_populator.getNode1().getId(), 2, newIfName));
        
        // add non existent snmpiface
        OnmsSnmpInterface snmpIface = new OnmsSnmpInterface();
        snmpIface.setIfIndex(2);
        snmpIface.setIfName(newIfName);
        
        m_upsertService.upsert(m_populator.getNode1().getId(), snmpIface, 0);

        assertEquals(0, countIfs(m_populator.getNode1().getId(), 2, oldIfName));
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 2, newIfName));
    }
    
    @Test
    @JUnitTemporaryDatabase
    @Transactional
    public void testConcurrentInsert() throws InterruptedException {
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 1, "atm0"));
        assertEquals(1, countIfs(m_populator.getNode1().getId(), 2, "eth0"));
        Inserter one = new Inserter(m_upsertService, m_populator.getNode1().getId(), 1001, "ifName1");
        Inserter two = new Inserter(m_upsertService, m_populator.getNode1().getId(), 1001, "ifName2");
        
        one.start();
        two.start();
        
        one.join();
        two.join();
        
        assertNull("Exception on upsert two "+two.getThrowable(), two.getThrowable());
        assertNull("Exception on upsert one "+one.getThrowable(), one.getThrowable());
    }

    private static class Inserter extends Thread {
        private static final AtomicInteger THREAD_ID = new AtomicInteger();
        private final UpsertService m_upsertService;
        private final int m_nodeId;
        private final int m_ifIndex;
        private final String m_ifName;
        private AtomicReference<Throwable> m_throwable = new AtomicReference<Throwable>();
        
        public Inserter(UpsertService upsertService, int nodeId, int ifIndex, String ifName) {
            super(Inserter.class.getName() + "-" + THREAD_ID.incrementAndGet());
            m_upsertService = upsertService;
            m_nodeId = nodeId;
            m_ifIndex = ifIndex;
            m_ifName = ifName;
        }
        
        @Override
        public void run() {
            try {
                OnmsSnmpInterface snmpIface = new OnmsSnmpInterface();
                snmpIface.setIfIndex(m_ifIndex);
                snmpIface.setIfName(m_ifName);
                m_upsertService.upsert(m_nodeId, snmpIface, 1000);
            } catch(Throwable t) {
                System.err.println("Caught exception in thread: " + Thread.currentThread().getName());
                t.printStackTrace();
                m_throwable.set(t);
            }
        }
        
        public Throwable getThrowable() {
            return m_throwable.get();
        }
    }
}
