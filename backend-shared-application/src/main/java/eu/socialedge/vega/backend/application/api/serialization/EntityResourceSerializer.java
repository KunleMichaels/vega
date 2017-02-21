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
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.ResourceSupport;

import static org.apache.commons.lang3.Validate.notNull;

public class EntityResourceSerializer<E extends Entity<?>, R extends ResourceSupport>
        extends ModelMapperSupport implements ResourceSerializer<E, R> {

    protected final Class<R> resourceClass;

    public EntityResourceSerializer(Class<R> resourceClass) {
        this(null, resourceClass);
    }

    public EntityResourceSerializer(ModelMapper modelMapper, Class<R> resourceClass) {
        super(modelMapper);
        this.resourceClass = notNull(resourceClass);
    }

    @Override
    public R toResource(E entity) {
        return modelMapper.map(entity, resourceClass);
    }
}
