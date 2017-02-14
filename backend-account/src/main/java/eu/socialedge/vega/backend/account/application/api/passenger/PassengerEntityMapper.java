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

import eu.socialedge.vega.backend.account.domain.Passenger;
import eu.socialedge.vega.backend.application.rest.serialization.EntityResourceMapper;
import eu.socialedge.vega.backend.payment.domain.Token;
import lombok.val;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class PassengerEntityMapper extends EntityResourceMapper<Passenger, PassengerResource> {

    private static final EmbeddedWrappers embeddedWrappers = new EmbeddedWrappers(true);

    public PassengerEntityMapper() {
        super(PassengerController.class, Passenger.class, PassengerResource.class);
    }

    @Override
    protected PassengerResource convertToResource(Passenger source) {
        val operatorResource = modelMapper.map(source, resourceClass);
        val tokenResources = source.paymentTokens().stream()
            .map(token -> modelMapper.map(token, TokenResource.class))
            .toArray(TokenResource[]::new);

        operatorResource.tokenResources(tokenResources);

        return operatorResource;
    }

    @Override
    protected Passenger convertFromResource(PassengerResource source) {
        val operatorEntity = modelMapper.map(source, entityClass);
        val tokenValueObjects = Arrays.stream(source.tokenResources())
            .map(tokenResource -> modelMapper.map(tokenResource, Token.class))
            .collect(Collectors.toList());

        tokenValueObjects.forEach(operatorEntity::addPaymentToken);

        return operatorEntity;
    }
}
