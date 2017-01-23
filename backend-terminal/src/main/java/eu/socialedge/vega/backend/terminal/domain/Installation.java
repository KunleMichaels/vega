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
package eu.socialedge.vega.backend.terminal.domain;

import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.ddd.ValueObject;
import eu.socialedge.vega.backend.fare.domain.VehicleType;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Embeddable @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Installation extends ValueObject {

    @Embedded
    private final OperatorId operatorId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final VehicleType vehicleType;

    @Column(nullable = false)
    private final String vehicleIdentifier;

    public Installation(OperatorId operatorId, VehicleType vehicleType, String vehicleIdentifier) {
        this.operatorId = notNull(operatorId);
        this.vehicleType = notNull(vehicleType);
        this.vehicleIdentifier = notEmpty(vehicleIdentifier);
    }
}
