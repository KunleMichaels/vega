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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.socialedge.vega.backend.geo.domain.Location;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.val;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class Path2ds {

    private final static ObjectMapper objectMapper = new ObjectMapper() {{
        setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }};

    private Path2ds() {
        throw new AssertionError("No Path2ds instance for you");
    }

    public static List<Location> extractVertices(Path2D path2d) {
        val pathIter = notNull(path2d).getPathIterator(null);
        val vertices = new ArrayList<Location>();

        val segment = new double[6];
        while(!pathIter.isDone()) {
            val segmentType = pathIter.currentSegment(segment);

            if (segmentType == PathIterator.SEG_MOVETO ||
                segmentType == PathIterator.SEG_LINETO) {
                vertices.add(new Location(segment[0], segment[1]));
            }

            pathIter.next();
        }

        return vertices;
    }

    public static String stringify(Path2D path2d) throws JsonProcessingException {
        val vertices = extractVertices(path2d);
        return objectMapper.writeValueAsString(vertices);
    }

    public static Path2D.Double parse(String json) throws IOException {
        if (isBlank(json))
            return null;

        List<Location> vertices = objectMapper.readValue(json,
            new TypeReference<List<Location>>() {});

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
