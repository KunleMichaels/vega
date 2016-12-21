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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Represents abstract identity of {@link Entity} objects
 *
 * @param <T> internal identifier's type
 */
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor(force = true)
public abstract class Identifier<T extends Serializable> extends ValueObject {

    protected final T value;

    public Identifier(T value) {
        this.value = notNull(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
