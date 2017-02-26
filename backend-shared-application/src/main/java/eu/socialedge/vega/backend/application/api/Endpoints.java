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
package eu.socialedge.vega.backend.application.api;

public interface Endpoints {

    String ROOT = "/api";

    String OPERATORS_ROOT = ROOT + "/operators";
    String OPERATORS_ID = OPERATORS_ROOT+ "/{operatorId}";

    String PASSENGERS_ROOT = ROOT + "/passengers";
    String PASSENGERS_ID = PASSENGERS_ROOT + "/{passengerId}";
    String PASSENGERS_TAGS = PASSENGERS_ROOT + "/{passengerId}/tags";
    String PASSENGERS_TAGS_ID = PASSENGERS_TAGS + "/{tagId}";

    String TAGS_ROOT = ROOT + "/tags";
    String TAGS_ID = TAGS_ROOT + "/{tagId}";

    String BOARDINGS_ROOT = ROOT + "/boardings";
    String BOARDINGS_ID = BOARDINGS_ROOT + "/{boardingId}";

    String TERMINALS_ROOT = ROOT + "/terminals";
    String TERMINALS_ID = TERMINALS_ROOT + "/{terminalId}";

    String FARES_ROOT = ROOT + "/fares";
    String FARES_ID = FARES_ROOT + "/{fareId}";
    String FARES_OPERATORS = FARES_ROOT + "/{fareId}/operators";
    String FARES_OPERATORS_ID = FARES_OPERATORS + "/{operatorId}";
}
