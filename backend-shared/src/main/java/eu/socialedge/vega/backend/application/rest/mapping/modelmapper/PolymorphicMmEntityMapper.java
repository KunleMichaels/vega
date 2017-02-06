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
import eu.socialedge.vega.backend.application.rest.mapping.EntitySerializer;
import eu.socialedge.vega.backend.application.rest.mapping.PolymorphicEntityManager;
import eu.socialedge.vega.backend.ddd.Entity;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;

public class PolymorphicMmEntityMapper<E extends Entity<?>, S, D> implements PolymorphicEntityManager<E, S, D> {

    private final ModelMapper modelMapper;

    private final EntitySerializer<E, D> entitySerializer;
    private final EntityDeserializer<E, S> entityDeserializer;

    public PolymorphicMmEntityMapper(Class<E> entityClass, Class<D> destClass) {
        this(null, entityClass, destClass);
    }

    public PolymorphicMmEntityMapper(ModelMapper modelMapper, Class<E> entityClass, Class<D> destClass) {
        if (modelMapper == null) {
            this.modelMapper = new ModelMapper();

            this.modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
        } else {
            this.modelMapper = modelMapper;
        }

        this.entitySerializer = new MmEntitySerializer<>(this.modelMapper, destClass);
        this.entityDeserializer = new MmEntityDeseriazlier<>(this.modelMapper, entityClass);
    }

    @Override
    public D serialize(E entity) {
        return entitySerializer.serialize(entity);
    }

    @Override
    public E deserialize(S source) {
        return entityDeserializer.deserialize(source);
    }
}
