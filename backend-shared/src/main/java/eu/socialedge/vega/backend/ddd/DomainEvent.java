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
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Domain Event is a type of message that describes something that
 * has happened in the past and is of interest to the business and domain.
 *
 * @see <a href="https://martinfowler.com/eaaDev/DomainEvent.html">
 *     Domain Event - Martin Fowler</a>
 */
@Getter
@Accessors(fluent = true)
public abstract class DomainEvent implements Serializable {

    protected final LocalDateTime creationTime = LocalDateTime.now();
}
