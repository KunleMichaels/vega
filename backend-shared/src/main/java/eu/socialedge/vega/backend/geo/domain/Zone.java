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

import eu.socialedge.vega.backend.ddd.ValueObject;
import eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert.Path2dSerializer;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
@ToString
@Accessors(fluent = true)
@Embeddable
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Zone extends ValueObject {

    @Column(name = "poly_path", nullable = false)
    @Convert(converter = Path2dSerializer.class)
    private final Path2D polyPath;

    private double[] expectedSegment;

    public Zone(List<Location> vertices) {
        this.polyPath = createPolygonPath(vertices);
    }

    public Zone(Location... vertices) {
        this(Arrays.asList(vertices));
    }

    public boolean contains(Location point) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        val actualZone = (Zone) o;
        val expectedPathIter = this.polyPath.getPathIterator(null);
        val actualPathIter = actualZone.polyPath.getPathIterator(null);

        val expectedSegment = new double[6];
        val actualSegment = new double[6];
        while (!expectedPathIter.isDone()) {

            int expectedSegmentType = expectedPathIter.currentSegment(expectedSegment);
            int actualSegmentType = actualPathIter.currentSegment(actualSegment);

            if (!Arrays.equals(expectedSegment, actualSegment) ||
                    expectedSegmentType != actualSegmentType)
                return false;

            expectedPathIter.next();
            actualPathIter.next();
        }

        if (!actualPathIter.isDone())
            return false;

        return true;
    }

    double[] aDouble = new double[2];

    @Override
    public int hashCode() {
        val pathIter = this.polyPath.getPathIterator(null);
        val hashCodeOperands = new LinkedList<Double>();

        val segment = new double[6];
        while (!pathIter.isDone()) {
            val segmentType = pathIter.currentSegment(segment);

            if (segmentType == PathIterator.SEG_MOVETO ||
                    segmentType == PathIterator.SEG_LINETO) {
                for(double p : segment) hashCodeOperands.add(p);
            }

            pathIter.next();
        }

        return hashCodeOperands.hashCode();
    }
}
