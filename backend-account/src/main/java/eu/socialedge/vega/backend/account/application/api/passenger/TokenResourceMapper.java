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
package eu.socialedge.vega.backend.account.application.api.passenger;

import eu.socialedge.vega.backend.application.api.resource.ResourceMapper;
import eu.socialedge.vega.backend.payment.domain.ExpirationDate;
import eu.socialedge.vega.backend.payment.domain.Token;
import lombok.val;

import static java.util.Objects.isNull;

public class TokenResourceMapper implements ResourceMapper<Token, TokenResource> {

    @Override
    public TokenResource toResource(Token entity) {
        if (isNull(entity)) return null;

        val resIdentifier = entity.identifier();
        val resExpirationDate = entity.expirationDate().toString();
        val resDescription = entity.description();
        val resIsPrimary = entity.isPrimary();

        return new TokenResource(resIdentifier, resExpirationDate, resDescription, resIsPrimary);
    }

    @Override
    public Token fromResource(TokenResource resource) {
        if (isNull(resource)) return null;

        val entIdentifier = resource.identifier();
        val entExpirationDate = ExpirationDate.parse(resource.expirationDate());
        val entDescription = resource.description();
        val entIsPrimary = resource.isPrimary();

        return new Token(entIdentifier, entExpirationDate, entDescription, entIsPrimary);
    }
}
