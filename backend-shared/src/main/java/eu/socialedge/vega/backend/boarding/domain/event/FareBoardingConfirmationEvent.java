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
package eu.socialedge.vega.backend.boarding.domain.event;

import eu.socialedge.vega.backend.account.domain.PassengerId;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.ddd.DomainEvent;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.geo.domain.Location;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * This event gets published by BoardingService when a passenger
 * boards the vehicle without a valid pass so he/she will be charged
 * in further processing steps.
 */
@Getter
@Builder
@Accessors(fluent = true)
public class FareBoardingConfirmationEvent extends DomainEvent {

    private final @NonNull TagId tagId;

    private final @NonNull FareId fareId;

    private final @NonNull PassengerId passengerId;

    private final @NonNull TerminalId terminalId;

    private final @NonNull Location location;
}
