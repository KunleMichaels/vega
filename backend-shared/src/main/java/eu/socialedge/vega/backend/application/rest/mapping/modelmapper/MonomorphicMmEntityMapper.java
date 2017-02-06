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

import eu.socialedge.vega.backend.ddd.Entity;
import org.modelmapper.ModelMapper;

public class MonomorphicMmEntityMapper<E extends Entity<?>, T> extends PolymorphicMmEntityMapper<E, T, T> {

    public MonomorphicMmEntityMapper(Class<E> entityClass, Class<T> destClass) {
        super(entityClass, destClass);
    }

    public MonomorphicMmEntityMapper(ModelMapper modelMapper, Class<E> entityClass, Class<T> destClass) {
        super(modelMapper, entityClass, destClass);
    }
}
