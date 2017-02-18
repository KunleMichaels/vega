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
package eu.socialedge.vega.backend.account.application.api.passenger;

import eu.socialedge.vega.backend.account.application.api.operator.OperatorController;
import eu.socialedge.vega.backend.account.domain.Passenger;
import eu.socialedge.vega.backend.application.api.serialization.EntityResourceMapper;
import eu.socialedge.vega.backend.payment.domain.Token;
import lombok.val;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PassengerEntityMapper extends EntityResourceMapper<Passenger, PassengerResource> {

    public PassengerEntityMapper() {
        super(Passenger.class, PassengerResource.class);
    }

    @Override
    public PassengerResource toResource(Passenger entity) {
        val operatorResource = modelMapper.map(entity, resourceClass);
        val tokenResources = entity.paymentTokens().stream()
            .map(token -> modelMapper.map(token, TokenResource.class))
            .toArray(TokenResource[]::new);

        operatorResource.tokenResources(tokenResources);

        val selfLink = linkTo(methodOn(PassengerController.class).read(entity.id())).withSelfRel();
        val tagsLink = linkTo(methodOn(PassengerController.class).tags(entity.id(),  null)).withRel("tags");
        operatorResource.add(selfLink, tagsLink);

        return operatorResource;
    }

    public Resources<PassengerResource> toResources(Collection<Passenger> passengers) {
        val resources = passengers.stream().map(this::toResource).collect(Collectors.toList());
        val resourcesCollection = new Resources<PassengerResource>(resources);

        val selfLink = linkTo(methodOn(OperatorController.class).read()).withSelfRel();
        resourcesCollection.add(selfLink);

        return resourcesCollection;
    }

    @Override
    public Passenger fromResource(PassengerResource resource) {
        val operatorEntity = modelMapper.map(resource, entityClass);
        val tokenValueObjects = Arrays.stream(resource.tokenResources())
            .map(tokenResource -> modelMapper.map(tokenResource, Token.class))
            .collect(Collectors.toList());

        tokenValueObjects.forEach(operatorEntity::addPaymentToken);

        return operatorEntity;
    }
}
