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

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import static java.util.Objects.requireNonNull;

/**
 * A {@link ValueObject} fundamentally defined not by its
 * attributes, but by a thread of continuity and identity.
 *
 * <p>In comparison to value objects, an entity’s core
 * trait is it’s identity. Two customers named Michael
 * Müller might not constitue the very same instance, so
 * that usually a dedicated property is introduced to capture
 * the identity. Another core trait of entities is that they’re
 * usually subject to a certain lifecycle within the problem
 * domain. They get created, they undergo certain state changes
 * usually driven by domain events and might reach an end state
 * (i.e. might be deleted, although this doesn’t necessary mean
 * that the information is removed from the system).</p>
 *
 * <p>Entities usually relate to other entities and contain
 * properties that are value objects or primitives (the former
 * preferred).</p>
 *
 * @see <a href="https://goo.gl/4UpUV8">
 *     Domain-Driven Design and Spring - Oliver Gierke</a>
 *
 * @param <T> concrete {@link Identifier} type implementation
 */
@Getter
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
@MappedSuperclass
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public abstract class Entity<T extends Identifier<?>> {

    @EmbeddedId
    private final T id;

    protected Entity(T id) {
        this.id = requireNonNull(id);
    }
}
