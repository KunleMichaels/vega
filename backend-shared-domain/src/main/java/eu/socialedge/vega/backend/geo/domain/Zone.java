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

import eu.socialedge.ddd.domain.ValueObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.val;

import static org.apache.commons.lang3.Validate.notEmpty;

@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Zone extends ValueObject {

    @ElementCollection
    private final List<Location> vertices;

    public Zone(Collection<Location> vertices) {
        this.vertices = new ArrayList<>(notEmpty(vertices));
    }

    public Zone(Location... vertices) {
        this(Arrays.asList(vertices));
    }

    public List<Location> vertices() {
        return Collections.unmodifiableList(vertices);
    }

    /**
     * Checks if the given point is contained inside the boundary.
     *
     * @see <a href="https://goo.gl/AG5UnC">
     *     http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html</a>
     * @see <a href="http://stackoverflow.com/a/23223947/2180005">
     *     http://stackoverflow.com/a/23223947/2180005</a>
     *
     * @param test the point to check
     * @return true if the point is inside the boundary, false otherwise
     */
    public boolean contains(Location test) {
        boolean contains = false;

        val testPointX = test.latitude();
        val testPointY = test.longitude();

        Location rayPoint1, rayPoint2;
        double rayPoint1X, rayPoint1Y,
               rayPoint2X, rayPoint2Y,
               intersectionX;

        for (int i = 0, j = vertices.size() - 1; i < vertices.size(); j = i++) {
            rayPoint1 = vertices.get(i);
            rayPoint1X = rayPoint1.latitude();
            rayPoint1Y = rayPoint1.longitude();

            rayPoint2 = vertices.get(j);
            rayPoint2X = rayPoint2.latitude();
            rayPoint2Y = rayPoint2.longitude();

            if ((rayPoint1Y > testPointY) == (rayPoint2Y > testPointY))
                continue;

            intersectionX =
                (rayPoint2X - rayPoint1X)
                    * (testPointY - rayPoint1Y) / (rayPoint2Y - rayPoint1Y)
                        + rayPoint1X;

            if (testPointX < intersectionX)
                contains = !contains;
        }

        return contains;
    }
}
