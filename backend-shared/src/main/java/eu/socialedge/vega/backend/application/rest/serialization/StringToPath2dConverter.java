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
package eu.socialedge.vega.backend.application.rest.serialization;

import eu.socialedge.vega.backend.util.Path2ds;

import org.springframework.core.convert.converter.Converter;

import java.awt.geom.Path2D;
import java.io.IOException;

public class StringToPath2dConverter implements Converter<Path2D, String> {

    @Override
    public String convert(Path2D source) {
        try {
            return Path2ds.stringify(source);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert Path2D to string", e);
        }
    }
}
