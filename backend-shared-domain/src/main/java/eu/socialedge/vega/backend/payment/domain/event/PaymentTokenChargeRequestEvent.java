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
package eu.socialedge.vega.backend.payment.domain.event;

import eu.socialedge.ddd.event.DomainEvent;
import eu.socialedge.vega.backend.payment.domain.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.money.MonetaryAmount;
import java.util.Map;

/**
 * This event represents a request to charge someone's tokenized
 * payment method.
 */
@Getter
@Builder
@Accessors(fluent = true)
public class PaymentTokenChargeRequestEvent extends DomainEvent {

    private final @NonNull Token token;

    private final @NonNull MonetaryAmount amount;

    private final @NonNull String description;

    private final Map<Object, Object> properties;
}
