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

/**
 * Conceptually a repository simulates a collection of aggregate
 * roots and allows accessing subsets or individual items.
 *
 * <p>They’re usually backed by some kind of persistence mechanism
 * but shouldn’t expose it to client code. Repositories refer to
 * entities, not the other way round.</p>
 *
 * @see <a href="https://goo.gl/mW15Nn">
 *     Domain-Driven Design and Spring - Oliver Gierke</a>
 *
 * @param <ID> concrete {@link Identifier} type implementation
 * @param <T> {@link AggregateRoot} type implementation
 */
public interface Repository<ID extends Identifier<?>, T extends AggregateRoot<ID>> {

    boolean contains(ID id);

    void clear();

    long size();

    boolean isEmpty();
}
