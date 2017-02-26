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

import eu.socialedge.vega.backend.boarding.domain.TagId;
import eu.socialedge.vega.backend.fare.domain.FareId;
import eu.socialedge.vega.backend.geo.domain.Location;
import eu.socialedge.vega.backend.terminal.domain.TerminalId;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Builder
@Getter @Setter
@AllArgsConstructor
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class BoardingResource {

    @NotNull(message = "Tag ID can not be null")
    private TagId tagId;

    @Deprecated // OAuth2 identification should be used later
    @NotNull(message = "Terminal ID can not be null")
    private TerminalId terminalId;

    private FareId fareId;

    @NotNull(message = "Location can not be null")
    private Location location;

}
