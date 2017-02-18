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
package eu.socialedge.vega.backend.application.api.serialization;

import eu.socialedge.ddd.domain.Entity;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.hateoas.ResourceSupport;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.Validate.notNull;

public class EntityResourceMapper<E extends Entity<?>, R extends ResourceSupport>
        implements ResourceMapper<E, R> {

    protected final Class<E> entityClass;

    protected final Class<R> resourceClass;

    protected ModelMapper modelMapper;

    public EntityResourceMapper(Class<E> entityClass, Class<R> resourceClass) {
        this(entityClass, resourceClass, null);
    }

    public EntityResourceMapper(Class<E> entityClass, Class<R> resourceClass, ModelMapper modelMapper) {
        this.entityClass = notNull(entityClass);
        this.resourceClass = notNull(resourceClass);
        this.modelMapper = isNull(modelMapper) ? createDefaultModelMapper() : modelMapper;
    }

    public R toResource(E entity) {
        return modelMapper.map(entity, resourceClass);
    }

    public E fromResource(R resource) {
        return modelMapper.map(resource, entityClass);
    }


    private ModelMapper createDefaultModelMapper() {
        val modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
            .setFieldMatchingEnabled(true)
            .setSourceNamingConvention(NamingConventions.NONE)
            .setDestinationNamingConvention(NamingConventions.NONE)
            .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
            .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);

        return modelMapper;
    }
}
