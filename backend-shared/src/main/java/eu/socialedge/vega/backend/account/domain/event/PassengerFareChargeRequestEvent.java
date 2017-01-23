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
package eu.socialedge.vega.backend.account.domain.event;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.ddd.DomainEvent;
import eu.socialedge.vega.backend.fare.domain.FareId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.money.MonetaryAmount;

/**
 * This event represents a request from the <b>Fare Microservice</b>
 * to pay fare that results to purchasing a new Pass. <b>Account
 * Microservice</b> places a token charge request to <b>Payment
 * Microservice</b> based on this request than.
 */
@Getter
@Builder
@Accessors(fluent = true)
public class PassengerFareChargeRequestEvent extends DomainEvent {

    private final @NonNull TagId tagId;

    private final @NonNull PassengerId passengerId;

    private final @NonNull FareId fareId;

    private final @NonNull MonetaryAmount monetaryAmount;
}
