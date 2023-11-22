package com.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.DropwizardWebServiceApplication;
import org.kainos.ea.DropwizardWebServiceConfiguration;
import org.kainos.ea.cli.Credential;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AuthIntegrationTest {

    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    Credential login = new Credential(
            System.getenv("TEST_VALID_EMAIL_API"),
            System.getenv("TEST_VALID_USER_PASSWORD_API")
    );

    @Test
    void postRegister_shouldReturnTokenOfUser() {

        String token = APP.client().target("http://localhost:8080/api/register")
                .request()
                .post(Entity.entity(login, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(String.class);

        Assertions.assertNotNull(token);
    }

    @Test
    void postLogin_shouldReturnTokenOfUser() {

        String token = APP.client().target("http://localhost:8080/api/login")
                .request()
                .post(Entity.entity(login, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(String.class);

        Assertions.assertNotNull(token);
    }

}
