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

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.socialedge.vega.backend.util.Path2ds;

import java.awt.geom.Path2D;
import java.io.IOException;

import javax.persistence.AttributeConverter;

public class Path2dAttributeConverter implements AttributeConverter<Path2D, String> {

    @Override
    public String convertToDatabaseColumn(Path2D path2d) {
        try {
            return Path2ds.stringify(path2d);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert Path2d to database column", e);
        }
    }

    @Override
    public Path2D convertToEntityAttribute(String verticesRaw) {
        try {
            return Path2ds.parse(verticesRaw);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert db column to Path2d", e);
        }
    }
}
