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
package eu.socialedge.vega.backend.fare.domain;

import org.apache.commons.lang3.Validate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Getter @Setter
@EqualsAndHashCode
@Accessors(fluent = true)
public class Operator {

    private final OperatorId id;

    private String name;

    private String description;

    public Operator(OperatorId id, String name, String description) {
        Validate.notNull(id);
        Validate.notBlank(name);

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void name(String name) {
        Validate.notBlank(name);
        this.name = name;
    }
}

