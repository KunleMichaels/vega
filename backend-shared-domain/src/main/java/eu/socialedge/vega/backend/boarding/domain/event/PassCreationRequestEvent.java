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

import eu.socialedge.ddd.event.DomainEvent;
import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.fare.domain.VehicleType;
import eu.socialedge.vega.backend.geo.domain.Zone;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.time.Period;
import java.util.Set;

/**
 * This event represents a request to create new Pass
 * for particular Tag based on date provided.
 */
@Getter
@Builder
@Accessors(fluent = true)
public class PassCreationRequestEvent extends DomainEvent {

    private final @NonNull TagId tagId;

    private final @NonNull FareId fareId;

    private final @NonNull Period validity;

    private final @NonNull Set<VehicleType> vehicleTypes;

    private final @NonNull Zone zone;

    private final @NonNull Set<OperatorId> operatorIds;
}
