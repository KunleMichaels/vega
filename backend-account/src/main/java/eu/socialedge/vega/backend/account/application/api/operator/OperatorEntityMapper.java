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
package eu.socialedge.vega.backend.account.application.api.operator;

import eu.socialedge.vega.backend.account.domain.Operator;
import eu.socialedge.vega.backend.application.api.serialization.EntityResourceMapper;
import lombok.val;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OperatorEntityMapper extends EntityResourceMapper<Operator, OperatorResource> {

    public OperatorEntityMapper() {
        super(Operator.class, OperatorResource.class);
    }

    @Override
    public OperatorResource toResource(Operator entity) {
        val resource = super.toResource(entity);

        val selfLink = linkTo(methodOn(OperatorController.class).read(entity.id())).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    public Resources<OperatorResource> toResources(Collection<Operator> operators) {
        val resources = operators.stream().map(this::toResource).collect(Collectors.toList());
        val resourcesCollection = new Resources<OperatorResource>(resources);

        val selfLink = linkTo(methodOn(OperatorController.class).read()).withSelfRel();
        resourcesCollection.add(selfLink);

        return resourcesCollection;
    }
}
