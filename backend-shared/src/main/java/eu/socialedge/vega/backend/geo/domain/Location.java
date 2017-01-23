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
package eu.socialedge.vega.backend.geo.domain;

import eu.socialedge.vega.backend.ddd.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.Validate;

@Getter
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(force = true)
public class Location extends ValueObject {

    private static final double LATITUDE_AMPLITUDE = 90;
    private static final double LONGITUDE_AMPLITUDE = 180;

    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        Validate.inclusiveBetween(-LATITUDE_AMPLITUDE, LATITUDE_AMPLITUDE, latitude);
        Validate.inclusiveBetween(-LONGITUDE_AMPLITUDE, LONGITUDE_AMPLITUDE, longitude);

        this.latitude = latitude;
        this.longitude = longitude;
    }
}