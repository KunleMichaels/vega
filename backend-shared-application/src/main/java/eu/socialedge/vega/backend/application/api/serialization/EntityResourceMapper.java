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
import org.springframework.hateoas.ResourceSupport;

public class EntityResourceMapper<E extends Entity<?>, R extends ResourceSupport>
        extends ModelMapperSupport
        implements ResourceMapper<E, R> {

    protected final ResourceSerializer<E, R> serializer;
    protected final ResourceDeserializer<E, R> deserializer;

    protected final Class<E> entityClass;
    protected final Class<R> resourceClass;

    public EntityResourceMapper(Class<E> entityClass, Class<R> resourceClass) {
        this.serializer = new EntityResourceSerializer<>(modelMapper, resourceClass);
        this.deserializer = new EntityResourceDeserializer<>(modelMapper, entityClass);

        this.entityClass = entityClass;
        this.resourceClass = resourceClass;
    }

    @Override
    public R toResource(E entity) {
        return serializer.toResource(entity);
    }

    @Override
    public E fromResource(R resource) {
        return deserializer.fromResource(resource);
    }
}
