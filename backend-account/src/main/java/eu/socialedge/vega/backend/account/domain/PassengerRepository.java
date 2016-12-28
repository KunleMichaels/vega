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
package eu.socialedge.vega.backend.account.domain;

import eu.socialedge.vega.backend.ddd.repository.CruaRepository;

public interface PassengerRepository extends CruaRepository<PassengerId, Passenger> {

    boolean isShadow(PassengerId PassengerId);

    default boolean isShadow(Passenger Passenger) {
        return isShadow(Passenger.id());
    }

    void shadow(PassengerId PassengerId);

    default void shadow(Passenger Passenger) {
        shadow(Passenger.id());
    }

    void unshadow(PassengerId PassengerId);

    default void unshadow(Passenger Passenger) {
        unshadow(Passenger.id());
    }
}
