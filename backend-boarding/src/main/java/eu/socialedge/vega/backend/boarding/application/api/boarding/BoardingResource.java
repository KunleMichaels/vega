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
package eu.socialedge.vega.backend.boarding.application.api.boarding;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import eu.socialedge.vega.backend.application.api.serialization.json.LocationDeserializer;
import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.geo.domain.Location;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
@Relation(collectionRelation = "boardings")
public class BoardingResource {

    @NotNull(message = "Tag ID can not be null")
    private TagId tagId;

    @Deprecated // OAuth2 identification should be used later
    @NotNull(message = "Terminal ID can not be null")
    private TerminalId terminalId;

    private FareId fareId;

    @JsonDeserialize(using = LocationDeserializer.class)
    @NotNull(message = "Location can not be null")
    private Location location;

}
