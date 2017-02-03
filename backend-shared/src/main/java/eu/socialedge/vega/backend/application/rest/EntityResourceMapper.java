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
package eu.socialedge.vega.backend.application.rest;

import eu.socialedge.vega.backend.ddd.Entity;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.stream.Collectors;

import lombok.val;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public abstract class EntityResourceMapper<E extends Entity<?>, R extends ResourceSupport> {

    private final Class<?> controller;

    private final Class<E> entityClass;

    private final Class<R> resourceClass;

    private ModelMapper modelMapper;

    public EntityResourceMapper(Class<?> controller, Class<E> entityClass, Class<R> resourceClass) {
        this(controller, entityClass, resourceClass, null);
    }

    public EntityResourceMapper(Class<?> controller, Class<E> entityClass, Class<R> resourceClass,
                                ModelMapper modelMapper) {

        this.controller = notNull(controller);
        this.entityClass = notNull(entityClass);
        this.resourceClass = notNull(resourceClass);

        if (modelMapper == null) {
            this.modelMapper = new ModelMapper();

            this.modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
        }
    }

    public R toResource(E entity) {
        val resource = convert(entity);

        resource.add(linkTo(controller).slash(entity.id()).withSelfRel());

        return resource;
    }

    public Collection<R> toResources(Collection<E> entities) {
        return entities.stream().map(this::toResource).collect(Collectors.toList());
    }

    public E fromResource(R resource) {
        return convert(resource);
    }

    public Collection<E> fromResource(Collection<R> resources) {
        return resources.stream().map(this::fromResource).collect(Collectors.toList());
    }

    protected R convert(E source) {
        return modelMapper.map(source, resourceClass);
    }

    protected E convert(R source) {
        return modelMapper.map(source, entityClass);
    }
}
