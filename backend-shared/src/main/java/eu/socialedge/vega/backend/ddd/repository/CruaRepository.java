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
import eu.socialedge.vega.backend.ddd.Deactivatable;
import eu.socialedge.vega.backend.ddd.Identifier;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CRUA - Create Read Update Activate/Deactivate {@link Repository}
 *
 * @param <ID> concrete {@link Identifier} type implementation
 * @param <T> {@link AggregateRoot} type implementation
 */
public interface CruaRepository<ID extends Identifier<?>, T extends AggregateRoot<ID> & Deactivatable>
        extends CruRepository<ID, T> {

    boolean isActive(ID id);

    default boolean isActive(T entity) {
        return isActive(entity.id());
    }

    void activate(ID id);

    default void activate(T entity) {
        activate(entity.id());
    }

    void activate(Iterable<ID> entityIds);

    default void activate(Collection<T> entities) {
        activate(entities.stream().map(T::id).collect(Collectors.toList()));
    }

    void deactivate(ID id);

    default void deactivate(T entity) {
        deactivate(entity.id());
    }

    void deactivate(Iterable<ID> entityIds);

    default void deactivate(Collection<T> entities) {
        deactivate(entities.stream().map(T::id).collect(Collectors.toList()));
    }

    Optional<T> getActive(ID id);

    Collection<T> listActive();
}