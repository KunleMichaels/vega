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
package eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.socialedge.vega.backend.geo.domain.Location;
import lombok.val;
import org.apache.commons.lang3.Validate;

import javax.persistence.AttributeConverter;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class Path2dSerializer implements AttributeConverter<Path2D, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper() {{
        setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }};

    @Override
    public String convertToDatabaseColumn(Path2D path2d) {
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

        try {
            return objectMapper.writeValueAsString(vertices);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Path2d to database column", e);
        }
    }

    @Override
    public Path2D convertToEntityAttribute(String verticesRaw) {
        Validate.notBlank(verticesRaw);

        try {
            List<Location> vertices = objectMapper.readValue(verticesRaw, new TypeReference<List<Location>>() {});

            Path2D.Double polyPath = new Path2D.Double();
            Location pathOrigin = vertices.get(0);

            polyPath.moveTo(pathOrigin.latitude(), pathOrigin.longitude());
            vertices.stream().skip(1).forEach(vertex -> {
                polyPath.lineTo(vertex.latitude(), vertex.longitude());
            });
            polyPath.closePath();

            return polyPath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert db column to Path2d", e);
        }


    }
}
