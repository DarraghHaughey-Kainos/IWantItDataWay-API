package com.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.DropwizardWebServiceApplication;
import org.kainos.ea.DropwizardWebServiceConfiguration;
import org.kainos.ea.cli.Specification;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SpecificationIntegrationTest {

    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    String validToken;

    @BeforeEach
    public void init() {
        String JWTSecret = System.getenv("JWT_SECRET");
        String expectedUsername = "ExpectedUsername";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(JWTSecret),
                SignatureAlgorithm.HS256.getJcaName());

        validToken = Jwts.builder()
                .claim("username", expectedUsername)
                .claim("role", "View")
                .setExpiration(DateUtils.addHours(new Date(), 1))
                .signWith(hmacKey)
                .compact();
    }

    @Test
    void getAllSpecifications_shouldReturnListOfSpecifications() {
        List<Specification> response = APP.client().target("http://localhost:8080/api/specifications")
                .request()
                .header("Authorization", validToken)
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);
    }
}
