package com.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.DropwizardWebServiceApplication;
import org.kainos.ea.DropwizardWebServiceConfiguration;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobRolesIntegrationTest {

    static final DropwizardAppExtension<DropwizardWebServiceConfiguration> APP = new DropwizardAppExtension<>(
            DropwizardWebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getJobRoles_shouldReturn200_whenValidRoleIsPassedIn () {

        String JWTSecret = System.getenv("JWT_SECRET");
        String expectedUsername = "ExpectedUsername";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(JWTSecret),
                SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .claim("username", expectedUsername)
                .claim("role", "View")
                .setExpiration(DateUtils.addHours(new Date(), 1))
                .signWith(hmacKey)
                .compact();

        Response response = APP.client().target("http://localhost:8080/api/job-roles")
                .request()
                .header("Authorization", token)
                .get();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void getJobRoles_shouldReturn401_whenInvalidRoleIsPassedIn () {

        String JWTSecret = System.getenv("JWT_SECRET");
        String expectedUsername = "ExpectedUsername";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(JWTSecret),
                SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .claim("username", expectedUsername)
                .claim("role", "Invalid Role")
                .setExpiration(DateUtils.addHours(new Date(), 1))
                .signWith(hmacKey)
                .compact();

        Response response = APP.client().target("http://localhost:8080/api/job-roles")
                .request()
                .header("Authorization", token)
                .get();

        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    void getJobRoleById_shouldReturn200_whenValidRoleIsPassedIn () {
        String JWTSecret = System.getenv("JWT_SECRET");
        String expectedUsername = "ExpectedUsername";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(JWTSecret),
                SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .claim("username", expectedUsername)
                .claim("role", "View")
                .setExpiration(DateUtils.addHours(new Date(), 1))
                .signWith(hmacKey)
                .compact();

        Response response = APP.client().target("http://localhost:8080/api/job-roles/2")
                .request()
                .header("Authorization", token)
                .get();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void getJobRoleById_shouldReturn401_whenInvalidRoleIsPassedIn () {
        String JWTSecret = System.getenv("JWT_SECRET");
        String expectedUsername = "ExpectedUsername";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(JWTSecret),
                SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .claim("username", expectedUsername)
                .claim("role", "Invalid Role")
                .setExpiration(DateUtils.addHours(new Date(), 1))
                .signWith(hmacKey)
                .compact();

        Response response = APP.client().target("http://localhost:8080/api/job-roles/2")
                .request()
                .header("Authorization", token)
                .get();

        Assertions.assertEquals(401, response.getStatus());
    }
}