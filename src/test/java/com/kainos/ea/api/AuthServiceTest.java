package com.kainos.ea.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.client.ValidationException;

import org.kainos.ea.core.CredentialValidator;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Connection;
import java.util.Base64;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    AuthDao authDao = Mockito.mock(AuthDao.class);
    CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    AuthService authService = new AuthService(databaseConnector, authDao, credentialValidator);
    Connection conn;

    Credential login = new Credential("test@test", "Test");

    @Test
    void register_shouldReturnToken_whenDaoDoesNotThrowException() throws ActionFailedException, ValidationException {
        String expectedResult = "test_jwt_token";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.generateToken(login.getEmail(), "View")).thenReturn(expectedResult);

        String result = authService.register(login);
        assertEquals(result, expectedResult);

    }

    @Test
    void register_shouldThrowActionedFailedException_whenDaoThrowsActionFailedException() throws ActionFailedException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.doThrow(ActionFailedException.class).when(authDao).registerUser(conn, login);

        assertThrows(ActionFailedException.class,
                () -> authService.register(login));
    }

    @Test
    void register_shouldThrowValidationFailedException_whenValidatorThrowsValidationException() throws ActionFailedException, ValidationException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.doThrow(ValidationException.class).when(credentialValidator).isValidUser(login);

        assertThrows(ValidationException.class,
                () -> authService.register(login));
    }


    @Test
    void login_shouldReturnToken_whenDaoReturnsTrue() throws ActionFailedException, AuthenticationException {
        String expectedResult = "test_jwt_token";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.validateLogin(conn, login)).thenReturn(true);
        Mockito.when(authDao.getUserRole(databaseConnector.getConnection(), login.getEmail())).thenReturn("View");
        Mockito.when(authDao.generateToken(login.getEmail(), "View")).thenReturn(expectedResult);

        String result = authService.login(login);
        assertEquals(result, expectedResult);
    }

    @Test
    void login_shouldThrowAuthenticationException_whenDaoReturnsFalse() throws ActionFailedException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.validateLogin(conn, login)).thenReturn(false);

        assertThrows(AuthenticationException.class,
                () -> authService.login(login));
    }

    @Test
    void login_shouldThrowActionedFailedException_whenDaoThrowsActionFailedException() throws ActionFailedException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.doThrow(ActionFailedException.class).when(authDao).validateLogin(conn, login);

        assertThrows(ActionFailedException.class,
                () -> authService.login(login));
    }

    @Test
    void isValidToken_shouldThrowAuthenticationException_whenNoTokenProvided() {
        assertThrows(AuthenticationException.class,
                () -> authService.isValidToken(null, "View"));
    }

    @ParameterizedTest
    @CsvSource({"View,View,False", "Admin,Admin,False", "Admin,View,False", "View,Admin,True", "Admin,NewRole,True"})
    void isValidToken_shouldThrowAuthenticationException_whenTokenSuccessfullyParsedWithViewRole(
            String setRole, String permission, String exception)
            throws AuthenticationException {

        Claims claims = null;
        String fakeSecret = "pSRQazqldmuUpzSyExp2VQr8k4j3hKqvNOgfSgEXq6ksm7y3";
        String expectedUsername = "ExpectedUsername";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(fakeSecret),
                SignatureAlgorithm.HS256.getJcaName());

        String token = Jwts.builder()
                .claim("username", expectedUsername)
                .claim("role", setRole)
                .signWith(hmacKey)
                .compact();

        claims = Jwts.parser()
                .setSigningKey(fakeSecret)
                .parseClaimsJws(token)
                .getBody();

        Mockito.when(authDao.parseToken(token)).thenReturn(claims);

        if (Objects.equals(exception, "True")) {
            assertThrows(AuthenticationException.class,
                    () -> authService.isValidToken(token, permission));
        } else {
            Claims result = authService.isValidToken(token, permission);
            String role = result.get("role").toString();
            String username = result.get("username").toString();

            assertNotNull(result);
            assertEquals(setRole, role);
            assertEquals(expectedUsername, username);
        }
    }
}
