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
package eu.socialedge.vega.backend.ddd.repository;

import eu.socialedge.vega.backend.ddd.AggregateRoot;
import eu.socialedge.vega.backend.ddd.Identifier;

import java.util.Collection;

/**
 * CRUA - Create Read Update Activate/Deactivate {@link Repository}
 *
 * @param <ID> concrete {@link Identifier} type implementation
 * @param <T> {@link AggregateRoot} type implementation
 */
public interface CruaRepository<ID extends Identifier<?>, T extends AggregateRoot<ID>> extends
        CruRepository<ID, T> {

    boolean isActive(ID id);

    boolean isActive(T object);

    void activate(ID id);

    void activate(T object);

    default void activate(Iterable<ID> entityIds) {
        entityIds.forEach(this::activate);
    }

    default void activate(Collection<T> objects) {
        objects.forEach(this::activate);
    }

    void deactivate(ID id);

    void deactivate(T object);

    default void deactivate(Iterable<ID> entityIds) {
        entityIds.forEach(this::deactivate);
    }

    default void deactivate(Collection<T> objects) {
        objects.forEach(this::deactivate);
    }
}