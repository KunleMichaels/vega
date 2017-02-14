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

import eu.socialedge.ddd.domain.Entity;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EntityResourceMapper<E extends Entity<?>, R extends ResourceSupport> {

    protected final Class<?> controller;

    protected final Class<E> entityClass;

    protected final Class<R> resourceClass;

    protected ModelMapper modelMapper;

    public EntityResourceMapper(Class<?> controller, Class<E> entityClass, Class<R> resourceClass) {
        this(controller, entityClass, resourceClass, null);
    }

    public EntityResourceMapper(Class<?> controller, Class<E> entityClass, Class<R> resourceClass, ModelMapper modelMapper) {
        this.controller = notNull(controller);
        this.entityClass = notNull(entityClass);
        this.resourceClass = notNull(resourceClass);
        this.modelMapper = isNull(modelMapper) ? createDefaultModelMapper() : modelMapper;
    }

    public R toResource(E entity) {
        val resource = convertToResource(entity);

        resource.add(linkTo(controller).slash(entity.id()).withSelfRel());

        return resource;
    }

    public Resources<R> toResources(Collection<E> entities) {
        val resources = entities.stream().map(this::toResource).collect(Collectors.toList());
        val resourceCollection = new Resources<R>(resources);

        resourceCollection.add(linkTo(controller).withSelfRel());

        return resourceCollection;
    }

    public E fromResource(R resource) {
        return convertFromResource(resource);
    }

    public Collection<E> fromResource(Resources<R> resources) {
        return StreamSupport.stream(resources.spliterator(), false)
            .map(this::fromResource)
            .collect(Collectors.toList());
    }

    protected R convertToResource(E source) {
        return modelMapper.map(source, resourceClass);
    }

    protected E convertFromResource(R source) {
        return modelMapper.map(source, entityClass);
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
