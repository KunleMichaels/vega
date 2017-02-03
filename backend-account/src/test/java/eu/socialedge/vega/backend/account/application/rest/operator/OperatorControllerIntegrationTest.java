/**
 * SocialEdge Vega - An Electronic Transit Fare Payment System Copyright (c) 2016 SocialEdge <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. <p> This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 */
package eu.socialedge.vega.backend.account.application.rest.operator;

import eu.socialedge.vega.backend.account.application.VegaAccountMicroservice;
import eu.socialedge.vega.backend.account.domain.Operator;
import eu.socialedge.vega.backend.account.domain.OperatorId;
import eu.socialedge.vega.backend.account.domain.OperatorRepository;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = VegaAccountMicroservice.class)
public class OperatorControllerIntegrationTest {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private OperatorRepository operatorRepository;

    private Operator operator;
    private OperatorResource operatorResource;

    @Before
    public void setUp() {
        operatorRepository.clear();

        operator = new Operator(randomOperatorId(), randomString(), randomString());
        operatorResource = new OperatorResource(operator.name(), operator.description());

        RestAssured.port = randomPort;
    }

    @Test
    public void shouldReturnValidOperator() {
        operatorRepository.add(operator);

        when()
            .get("/api/operators/{id}", operator.id().toString())
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("name", Matchers.is(operator.name()))
            .body("description", Matchers.is(operator.description()));
    }

    private static String randomString() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    private static OperatorId randomOperatorId() {
        long id = ThreadLocalRandom.current().nextLong(1, 1000);
        return new OperatorId(id);
    }
}
