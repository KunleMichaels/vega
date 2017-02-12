package eu.socialedge.vega.backend.application.rest;

import eu.socialedge.ddd.domain.Entity;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EntityResourceMapper<E extends Entity<?>, R extends ResourceSupport> {

    private final Class<?> controller;

    private final Class<E> entityClass;

    private final Class<R> resourceClass;

    private ModelMapper modelMapper;

    public EntityResourceMapper(Class<?> controller, Class<E> entityClass, Class<R> resourceClass) {
        this(controller, entityClass, resourceClass, null);
    }

    public EntityResourceMapper(Class<?> controller, Class<E> entityClass, Class<R> resourceClass, ModelMapper modelMapper) {
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
        } else {
            this.modelMapper = modelMapper;
        }
    }

    public R toResource(E entity) {
        val resource = convertToResource(entity);

        resource.add(linkTo(controller).slash(entity.id()).withSelfRel());

        return enhanceToResource(resource, entity);
    }

    public Collection<R> toResources(Collection<E> entities) {
        return entities.stream().map(this::toResource).collect(Collectors.toList());
    }

    public E fromResource(R resource) {
        val entity = convertFromResource(resource);

        return enhanceFromResource(entity, resource);
    }

    public Collection<E> fromResource(Collection<R> resources) {
        return resources.stream().map(this::fromResource).collect(Collectors.toList());
    }

    protected R convertToResource(E source) {
        return modelMapper.map(source, resourceClass);
    }

    protected E convertFromResource(R source) {
        return modelMapper.map(source, entityClass);
    }

    protected R enhanceToResource(R resource, E entity) {
        return resource;
    }

    protected E enhanceFromResource(E entity, R resource) {
        return entity;
    }
}
