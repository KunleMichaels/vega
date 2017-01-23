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
package eu.socialedge.vega.backend.ddd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Represents a DDD Aggregate Root - a cluster of event
 * objects that can be treated as a single unit.
 *
 * <p>An aggregate will have one of its component objects be the
 * aggregate root. Any references from outside the aggregate should
 * only go to the aggregate root. The root can thus ensure the integrity
 * of the aggregate as a whole.</p>
 *
 * @see <a href="https://martinfowler.com/bliki/DDD_Aggregate.html">
 *     DDD_Aggregate - Martin Fowler</a>
 */
@Getter @Setter
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public abstract class AggregateRoot<T extends Identifier<?>> extends Entity<T> {

    protected AggregateRoot(T id) {
        super(id);
    }
}
