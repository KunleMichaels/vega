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
package eu.socialedge.vega.backend.transit.domain.location;

import eu.socialedge.vega.backend.ddd.AggregateRoot;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;

@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Zone extends AggregateRoot<ZoneId> {

    @Getter
    @Column(nullable = false)
    private final String name;

    @Getter
    @ElementCollection
    @CollectionTable(name = "zone_vertex", joinColumns = @JoinColumn(name = "zone_id"))
    private final List<Location> vertices;

    private transient Path2D polyPath;

    public Zone(String name, List<Location> vertices) {
        this.name = notBlank(name);
        this.vertices = notEmpty(vertices);
    }

    public Zone(String name, Location... vertices) {
        this(name, Arrays.asList(vertices));
    }

    public boolean contains(Location point) {
        if (polyPath == null) {
            synchronized (this) {
                polyPath = createPolygonPath(vertices);
            }
        }
        return polyPath.contains(point.latitude(), point.longitude());
    }

    private static Path2D.Double createPolygonPath(List<Location> vertices) {
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
