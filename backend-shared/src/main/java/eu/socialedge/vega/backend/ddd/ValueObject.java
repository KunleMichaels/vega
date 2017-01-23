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

import java.io.Serializable;

/**
 * Represents a concept from the event that is considered a value.
 *
 * <p>In terms of classes that means that individual instances don’t
 * have identity, no lifecycle. A value object needs to be immutable
 * to ensure integrity of the instances as they can be shared amongst
 * different consumers.</p>
 *
 * <p> Value objects are usually part of other model elements like
 * {@link Entity} or {@link AggregateRoot}.</p>
 *
 * @see <a href="https://goo.gl/4UpUV8">
 *     Domain-Driven Design and Spring - Oliver Gierke</a>
 */
public abstract class ValueObject implements Serializable {
}
