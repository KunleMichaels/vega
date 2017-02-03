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
package eu.socialedge.vega.backend.infrastructure.eventbus.cloudstream.config;

import lombok.val;
import net.manub.embeddedkafka.EmbeddedKafka$;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import scala.collection.immutable.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * This configuration starts embedded Kafka broker if
 * Spring's {@code dev} profile is activated
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration
@Profile("dev")
public class EmbeddedKafkaConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedKafkaConfig.class);

    private static final String KF_PORT_PROP = "spring.cloud.stream.kafka.binder.defaultBrokerPort";
    private static final String ZK_PORT_PROP = "spring.cloud.stream.kafka.binder.defaultZkPort";

    private static final int DEFAULT_EMBEDDED_ZK_PORT = 2181;
    private static final int DEFAULT_EMBEDDED_KF_PORT = 9092;

    @Autowired
    private Environment env;

    private boolean embeddedKafkaStarted = false;

    @PostConstruct
    private void startEmbeddedKafkaServer() {
        val kfPort = env.getProperty(KF_PORT_PROP, Integer.class, DEFAULT_EMBEDDED_KF_PORT);
        val zkPort = env.getProperty(ZK_PORT_PROP, Integer.class, DEFAULT_EMBEDDED_ZK_PORT);

        if (portsAvailable(kfPort, zkPort)) {
            val config = new net.manub.embeddedkafka.EmbeddedKafkaConfig(kfPort, zkPort, new HashMap<>());

            logger.info("Setting up Embedded kafka server ...");
            EmbeddedKafka$.MODULE$.start(config);
            embeddedKafkaStarted = true;

            logger.info("Embedded kafka server started. Zk port = {}, kafka port = {}", zkPort, kfPort);
        } else {
            logger.info("Embedded kafka server NOT started. Zk port = {} or kafka port = {} already in use",
                zkPort, kfPort);
        }
    }

    @PreDestroy
    private void stopEmbeddedKafkaServer() {
        if (embeddedKafkaStarted) {
            logger.info("Shutting down embedded kafka");
            EmbeddedKafka$.MODULE$.stop();
        }
    }

    private static boolean portsAvailable(int... ports) {
        return Arrays.stream(ports).allMatch(EmbeddedKafkaConfig::portAvailable);
    }

    private static boolean portAvailable(int port) {
        try (val socket = new Socket("localhost", port)) {
            return false; // something is using the port and has responded
        } catch (IOException e) {
            return true;
        }
    }
}
