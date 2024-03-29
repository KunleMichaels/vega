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
package eu.socialedge.vega.backend.geo.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @see <a href="https://goo.gl/bak05V">Zone Shape Render used for tests</a>
 */
public class ZoneTest {
    private static final List<Location> zoneVertices = new ArrayList<Location>() {{
        add(new Location(1, 2));
        add(new Location(6, 6));
        add(new Location(10, 1));
        add(new Location(6, 3));
    }};

    private static final Zone zone = new Zone(zoneVertices);

    private static final List<Location> goodCoords = new ArrayList<Location>() {{
        add(new Location(5, 3));
        add(new Location(6, 4));
        add(new Location(6, 5));
        add(new Location(8, 3));
        add(new Location(9, 2));
    }};

    private static final List<Location> badCoords = new ArrayList<Location>() {{
        add(new Location(1, 3));
        add(new Location(6, 2));
        add(new Location(6, 2.5));
        add(new Location(6, 6.5));
        add(new Location(5, 2));
        add(new Location(4, 5));
        add(new Location(9, 1));
        add(new Location(9, 3));
    }};

    @Test
    public void shouldContainVerticesInsideItsArea() {
        goodCoords.forEach(location -> {
            assertTrue(zone.contains(location));
        });
    }

    @Test
    public void shouldNotContainVerticesOutsideItsArea() {
        badCoords.forEach(location -> {
            assertFalse(zone.contains(location));
        });
    }

    @Test
    public void shouldCheckForEqualityCorrectly() {
        assertEquals(zone, new Zone(zoneVertices));
    }

    @Test
    public void shouldGenerateCorrectHashCode() {
        assertEquals(zone.hashCode(), new Zone(zoneVertices).hashCode());
    }
}