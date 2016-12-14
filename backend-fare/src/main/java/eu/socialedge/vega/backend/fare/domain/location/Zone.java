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
package eu.socialedge.vega.backend.fare.domain.location;

import eu.socialedge.vega.backend.ddd.ValueObject;

import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Zone extends ValueObject {

    private final String name;

    private final Path2D polyPath;

    public Zone(String name, List<Location> vertices) {
        this.name = notBlank(name);
        this.polyPath = createPolygonPath(notEmpty(vertices));
    }

    public Zone(String name, Location... vertices) {
        this(name, Arrays.asList(vertices));
    }

    public boolean contains(Location point) {
        return polyPath.contains(point.latitude(), point.longitude());
    }

    private Path2D.Double createPolygonPath(List<Location> vertices) {
        Path2D.Double polyPath = new Path2D.Double();
        Location pathOrigin = vertices.get(0);

        polyPath.moveTo(pathOrigin.latitude(), pathOrigin.longitude());
        vertices.stream().skip(1).forEach(vertex -> {
            polyPath.lineTo(vertex.latitude(), vertex.longitude());
        });
        polyPath.closePath();

        return polyPath;
    }
}
