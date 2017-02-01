/**
 * SocialEdge Vega - An Electronic Transit Fare Payment System
 * Copyright (c) 2016 SocialEdge
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package eu.socialedge.vega.backend.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.socialedge.vega.backend.geo.domain.Location;

import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Path2dsTest {

    private final static ObjectMapper objectMapper = new ObjectMapper() {{
        setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }};

    private static final List<Location> zoneVertices = new ArrayList<Location>() {{
        add(new Location(1, 2));
        add(new Location(6, 6));
        add(new Location(10, 1));
        add(new Location(6, 3));
    }};

    private static final Path2D zonePath2d = new Path2D.Double() {{
        Location pathOrigin = zoneVertices.get(0);
        moveTo(pathOrigin.latitude(), pathOrigin.longitude());

        zoneVertices.stream().skip(1).forEach(vertex -> {
            lineTo(vertex.latitude(), vertex.longitude());
        });

        closePath();
    }};

    @Test
    public void shouldStringifyPath2dCorrectly() throws Exception {
        String serializedPath2d = Path2ds.stringify(zonePath2d);

        assertEquals(objectMapper.writeValueAsString(zoneVertices), serializedPath2d);
    }

    @Test
    public void shouldParsePath2dCorrectly() throws Exception {
        String json = objectMapper.writeValueAsString(zoneVertices);
        Path2D path = Path2ds.parse(json);

        assertEqualsPath2d(zonePath2d, path);
    }

    private void assertEqualsPath2d(Path2D expected, Path2D actual) {
        PathIterator expectedPathIter = expected.getPathIterator(null);
        PathIterator actualPathIter = actual.getPathIterator(null);

        double[] expectedSegment = new double[6];
        double[] actualSegment = new double[6];
        while (!expectedPathIter.isDone()) {

            int expectedSegmentType = expectedPathIter.currentSegment(expectedSegment);
            int actualSegmentType = actualPathIter.currentSegment(actualSegment);

            assertArrayEquals(expectedSegment, actualSegment, 0.01D);
            assertEquals(expectedSegmentType, actualSegmentType);

            expectedPathIter.next();
            actualPathIter.next();
        }

        assertTrue(actualPathIter.isDone());
    }
}
