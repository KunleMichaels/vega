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
package eu.socialedge.vega.backend.account.application.rest.operator;

import eu.socialedge.vega.backend.account.domain.OperatorRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

public class PolyOperatorController {

    @Autowired
    private PolyOperatorEntityMapper mapper;

    @Autowired
    private OperatorRepository operatorRepository;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @NotNull NewOperatorResource newOperatorResource,
                                       UriComponentsBuilder urlBuilder) {

        val operator = mapper.deserialize(newOperatorResource);
        operatorRepository.add(operator);

        val url = urlBuilder.path("/user/{id}").buildAndExpand(operator.id()).toUri();
        return ResponseEntity.created(url).build();
    }
}
