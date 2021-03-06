/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012-2014 The OpenNMS Group, Inc.
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

package org.opennms.features.topology.app.internal;

import java.util.ArrayList;
import java.util.List;

import org.opennms.features.topology.api.browsers.ContentType;
import org.opennms.features.topology.api.browsers.SelectionChangedListener;
import org.opennms.features.topology.api.topo.AbstractTopologyProvider;
import org.opennms.features.topology.api.topo.Defaults;
import org.opennms.features.topology.api.topo.Edge;
import org.opennms.features.topology.api.topo.GraphProvider;
import org.opennms.features.topology.api.topo.Vertex;
import org.opennms.features.topology.api.topo.VertexRef;

public class TestTopologyProvider extends AbstractTopologyProvider implements GraphProvider {

    public TestTopologyProvider() {
        super("test");
        
        resetContainer();
        
        String vId1 = getNextVertexId();
        TestVertex v1 = new TestVertex(vId1, 0, 0);
        v1.setLabel("a leaf");
        
        addVertices(v1);
        
        String vId2 = getNextVertexId();
        TestVertex v2 = new TestVertex(vId2, 0, 0);
        v2.setLabel("another leaf");
        addVertices(v2);
        
        Edge edge = connectVertices(v1, v2);
        edge.setStyleName("default");
    }

    @Override
    public void refresh() {
        clearEdges();
        clearVertices();

        List<TestVertex> vertices = new ArrayList<TestVertex>();

        String vId1 = getNextVertexId();
        TestVertex v1 = new TestVertex(vId1, 0, 0);
        v1.setLabel("a leaf vertex");

        vertices.add(v1);

        String vId2 = getNextVertexId();
        TestVertex v2 = new TestVertex(vId2, 0, 0);
        v2.setLabel("another leaf");
        vertices.add(v2);

        addVertices(vertices.toArray(new Vertex[0]));

        connectVertices(v1, v2);
    }

    @Override
    public Defaults getDefaults() {
        return new Defaults();
    }

    @Override
    public SelectionChangedListener.Selection getSelection(List<VertexRef> selectedVertices, ContentType type) {
        return SelectionChangedListener.Selection.NONE;
    }

    @Override
    public boolean contributesTo(ContentType type) {
        return false;
    }
}
