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

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Accessors(fluent = true)
@Embeddable @Access(AccessType.FIELD)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class Token {

    @Column(name = "token_id", nullable = false)
    private final @NonNull String tokenId;

    @Embedded
    private final @NonNull ExpirationDate expirationDate;

    @Column
    private final String description;
}
