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
package eu.socialedge.vega.backend.fare.domain.event;

import eu.socialedge.ddd.event.DomainEvent;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * This event represents a confirmation from the <b>Account
 * Microservice</b> that passenger has paid the fare so it
 * should be booked.
 * <p>
 * This event results in new
 * {@link eu.socialedge.vega.backend.boarding.domain.event.PassCreationRequestEvent}
 * that will create new pass for specific Tag.
 */
@Getter
@Builder
@Accessors(fluent = true)
public class BookFarePaymentRequestEvent extends DomainEvent {

    private final @NonNull TagId tagId;

    private final @NonNull FareId fareId;
}
