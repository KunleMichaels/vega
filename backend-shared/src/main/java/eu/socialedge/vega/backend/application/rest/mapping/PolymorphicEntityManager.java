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
package eu.socialedge.vega.backend.application.rest.mapping;

import eu.socialedge.vega.backend.ddd.Entity;

/**
 * Performs bidirectional entity mapping where source and destination
 * types are different.
 *
 * <p>
 * Mapper allows to convert E entity to T type and backwards from
 * S type to E entity.
 *
 * @param <E> entity type
 * @param <D> destination type
 * @param <S> source type
 */
public interface PolymorphicEntityManager<E extends Entity<?>, S, D>
        extends EntitySerializer<E, D>, EntityDeserializer<E, S> {
}
