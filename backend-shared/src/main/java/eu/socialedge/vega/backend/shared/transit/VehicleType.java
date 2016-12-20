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
package eu.socialedge.vega.backend.shared.transit;

import javax.persistence.Embeddable;

/**
 * Describes the type of transportation the Fare services and
 * Terminal's installation vehicle type
 *
 * @see <a href="https://goo.gl/XiMs19">
 *     Google Maps API - Directions Service - Vehicle Type</a>
 */
@Embeddable
public enum VehicleType {

    RAIL,

    METRO_RAIL,

    SUBWAY,

    TRAM,

    MONORAIL,

    HEAVY_RAIL,

    COMMUTER_TRAIN,

    HIGH_SPEED_TRAIN,

    BUS,

    INTERCITY_BUS,

    TROLLEYBUS,

    /**
     * Share taxi is a kind of bus with the ability to drop
     * off and pick up passengers anywhere on its route.
     */
    SHARE_TAXI,

    FERRY,

    /**
     * A vehicle that operates on a cable, usually on the ground.
     * Aerial cable cars may be of the type VehicleType.GONDOLA_LIFT.
     */
    CABLE_CAR,

    /**
     * An aerial cable car.
     */
    GONDOLA_LIFT,

    /**
     * A vehicle that is pulled up a steep incline by a cable. A Funicular typically
     * consists of two cars, with each car acting as a counterweight for the other.
     */
    FUNICULAR,

    /**
     * All other vehicles will return this type.
     */
    OTHER
}
