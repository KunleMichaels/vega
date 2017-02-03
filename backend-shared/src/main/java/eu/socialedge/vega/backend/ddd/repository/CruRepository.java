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
import java.util.Optional;

/**
 * CRU - Create Read Update {@link Repository}
 *
 * @param <ID> concrete {@link Identifier} type implementation
 * @param <T> {@link AggregateRoot} type implementation
 */
public interface CruRepository<ID extends Identifier<?>, T extends AggregateRoot<ID>> extends
        Repository<ID, T> {

    void add(T entity);

    void add(Collection<T> entities);

    void update(T entity);

    void update(Collection<T> entities);

    Optional<T> get(ID id);

    Collection<T> list();

    void clear();
}
