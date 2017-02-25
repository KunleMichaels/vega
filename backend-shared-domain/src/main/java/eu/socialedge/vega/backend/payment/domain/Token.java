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

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static org.apache.commons.lang3.StringUtils.stripToNull;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Getter
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
@Embeddable @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Token {

    private final static String DEFAULT_TOKEN_DESC_FORMAT = "Token-%s";

    @Column(name = "identifier", nullable = false)
    private final @NonNull String identifier;

    @Embedded
    private final @NonNull ExpirationDate expirationDate;

    @Column
    private final String description;

    @Column
    private final boolean isPrimary;

    public Token(String identifier, ExpirationDate expirationDate, String description,
                 boolean isPrimary) {
        this.identifier = notBlank(identifier);
        this.expirationDate = notNull(expirationDate);
        this.description = stripToNull(description);
        this.isPrimary = isPrimary;
    }

    public Token(String identifier, ExpirationDate expirationDate) {
        this(identifier, expirationDate, createTokenDescription(identifier), false);
    }

    public Token(String identifier, ExpirationDate expirationDate, boolean isPrimary) {
        this(identifier, expirationDate, createTokenDescription(identifier), isPrimary);
    }

    private static String createTokenDescription(String identifier) {
        return String.format(DEFAULT_TOKEN_DESC_FORMAT, identifier);
    }

    public boolean isExpired() {
        return expirationDate.occurred();
    }
}
