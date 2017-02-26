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
package eu.socialedge.vega.backend.payment.domain;

import eu.socialedge.ddd.domain.ValueObject;
import lombok.Getter;
import lombok.experimental.Accessors;

import static org.apache.commons.lang3.StringUtils.stripToNull;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@Accessors(fluent = true)
public abstract class Transaction extends ValueObject {

    protected final String id;

    protected final Token token;

    protected final String description;

    protected Transaction(String id, Token token, String description) {
        this.id = notNull(id);
        this.token = notNull(token);
        this.description = stripToNull(description);
    }

    protected Transaction(String id, Token token) {
        this(id, token, null);
    }
}
