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

import eu.socialedge.vega.backend.application.api.Endpoints;
import eu.socialedge.vega.backend.boarding.domain.BoardingService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class BoardingController {

    @Autowired
    private BoardingService boardingService;

    @Transactional
    @RequestMapping(method = POST, path = Endpoints.BOARDINGS_ROOT)
    public ResponseEntity<Void> board(@RequestBody @NotNull @Valid BoardingResource boardingResource) {
        val fareId = boardingResource.fareId();
        val tagId = boardingResource.tagId();
        val terminalId = boardingResource.terminalId();
        val location = boardingResource.location();

        val isPaidBoarding = fareId != null;
        if (isPaidBoarding) {
            boardingService.board(tagId, terminalId, fareId, location);
        } else {
            boardingService.board(tagId, terminalId, location);
        }

        return ResponseEntity.ok().build();
    }

}
