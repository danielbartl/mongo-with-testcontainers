package com.danielbartl.mongodb;

import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoDBSetupTest {


    @Test
    @DisplayName("Verifies whether the setup of testcontainers with the latest mongodb docker image is working properly.")
    void verifySetUp() {

        try (final var mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))) {

            mongoDBContainer.start();

            try (final var client = MongoClients.create(mongoDBContainer.getConnectionString())) {

                final ArrayList<String> list = client.listDatabaseNames().into(new ArrayList<>());

                assertEquals(3, list.size());

                assertTrue(list.contains("admin"));
                assertTrue(list.contains("local"));
                assertTrue(list.contains("config"));

            }

        }

    }

}
