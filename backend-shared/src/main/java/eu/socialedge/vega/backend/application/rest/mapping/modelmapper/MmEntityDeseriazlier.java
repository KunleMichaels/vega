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
package eu.socialedge.vega.backend.application.rest.mapping.modelmapper;

import eu.socialedge.vega.backend.application.rest.mapping.EntityDeserializer;
import eu.socialedge.vega.backend.ddd.Entity;
import org.modelmapper.ModelMapper;

public class MmEntityDeseriazlier<E extends Entity<?>, S> implements EntityDeserializer<E, S> {

    private final ModelMapper modelMapper;

    private final Class<E> entityClass;

    public MmEntityDeseriazlier(ModelMapper modelMapper, Class<E> entityClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
    }

    @Override
    public E deserialize(S source) {
        return modelMapper.map(source, entityClass);
    }
}
