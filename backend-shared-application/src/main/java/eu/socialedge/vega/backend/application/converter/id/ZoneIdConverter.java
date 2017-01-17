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
package eu.socialedge.vega.backend.application.converter.id;

import eu.socialedge.vega.backend.transit.domain.location.ZoneId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ZoneIdConverter implements Converter<Serializable, ZoneId> {

    @Override
    public ZoneId convert(Serializable source) {
        return new ZoneId(Long.valueOf(source.toString()));
    }
}
