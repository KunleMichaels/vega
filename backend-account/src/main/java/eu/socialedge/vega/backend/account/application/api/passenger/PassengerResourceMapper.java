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
import eu.socialedge.vega.backend.application.api.resource.ResourceApplier;
import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.payment.domain.Token;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static java.util.Objects.isNull;

@Component
public class PassengerResourceMapper
        implements ResourceMapper<Passenger,PassengerResource>, ResourceApplier<Passenger, PassengerResource> {

    private static final TokenResourceMapper tokenResourceMapper = new TokenResourceMapper();

    @Override
    public PassengerResource toResource(Passenger entity) {
        if (isNull(entity)) return null;

        val resId = entity.id();
        val resName = entity.name();
        val resEmail = entity.email();
        val resTagIds = entity.tagIds();
        val resTokens = tokenResourceMapper.toResources(entity.paymentTokens());

        return PassengerResource.builder()
            .id(resId)
            .name(resName)
            .email(resEmail)
            .tagIds(resTagIds)
            .tokenResources(resTokens)
            .build();
    }

    @Override
    public void applyResource(PassengerResource resource, Passenger entity) {
        val entName = resource.name();
        val entEmail = resource.email();
        val entPassword = resource.password();
        val entTagIds = resource.tagIds();
        val entTokens = tokenResourceMapper.fromResources(resource.tokenResources());

        if (!isNull(entName)) entity.name(entName);
        if (!isNull(entEmail)) entity.email(entEmail);
        if (!isNull(entPassword)) entity.password(entPassword);

        if (!isNull(entTagIds)) {
            entity.removeTagIds();
            entTagIds.forEach(entity::addTagId);
        }

        if (!isNull(entTokens)) {
            entity.removePaymentTokens();
            entTokens.forEach(entity::addPaymentToken);
        }
    }

    @Override
    public Passenger fromResource(PassengerResource resource) {
        if (isNull(resource)) return null;

        val entName = resource.name();
        val entEmail = resource.email();
        val entPassword = resource.password();
        val entTagIds = new HashSet<TagId>(resource.tagIds());
        val entTokens = new HashSet<Token>(tokenResourceMapper.fromResources(resource.tokenResources()));

        return new Passenger(entName, entEmail, entPassword, entTagIds, entTokens);
    }
}
