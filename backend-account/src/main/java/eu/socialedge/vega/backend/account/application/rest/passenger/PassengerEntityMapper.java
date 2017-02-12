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
package eu.socialedge.vega.backend.account.application.rest.passenger;

import eu.socialedge.vega.backend.account.domain.Passenger;
import eu.socialedge.vega.backend.application.rest.EntityResourceMapper;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class PassengerEntityMapper extends EntityResourceMapper<Passenger, PassengerResource> {

    public PassengerEntityMapper() {
        super(PassengerController.class, Passenger.class, PassengerResource.class);
    }

    @Override
    public PassengerResource enhanceToResource(PassengerResource passengerResource, Passenger entity) {
        passengerResource.add(
            linkTo(methodOn(PassengerController.class).tokens(entity.id())).withRel("tokens"),
            linkTo(methodOn(PassengerController.class).tags(entity.id(), null)).withRel("tags"),
            linkTo(methodOn(PassengerController.class).addTag(entity.id(), null)).withRel("addTag"),
            linkTo(methodOn(PassengerController.class).removeTag(entity.id(), null)).withRel("removeTag")
        );

        return passengerResource;
    }
}
