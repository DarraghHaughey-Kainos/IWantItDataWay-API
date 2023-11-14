package com.kainos.ea.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    AuthDao authDao = Mockito.mock(AuthDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    AuthService authService = new AuthService(databaseConnector, authDao);
    Connection conn;

    Credential login = new Credential("TestUser", "Test");

    @Test
    void register_shouldReturnToken_whenDaoDoesNotThrowException() throws ActionFailedException {
        String expectedResult = "test_jwt_token";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.generateToken(login.getUsername())).thenReturn(expectedResult);

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
    void login_shouldReturnToken_whenDaoReturnsTrue() throws ActionFailedException, AuthenticationException {
        String expectedResult = "test_jwt_token";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.validateLogin(conn, login)).thenReturn(true);
        Mockito.when(authDao.generateToken(login.getUsername())).thenReturn(expectedResult);

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
                () -> authService.isValidToken(null));
    }

    @Test
    void isValidToken_shouldReturnClaims_whenTokenSuccessfullyParsed() throws AuthenticationException {
        Claims expectedResult = new DefaultClaims();
        String token = "test-token";

        Mockito.when(authDao.parseToken(token)).thenReturn(expectedResult);

        Claims result = authService.isValidToken(token);
        assertEquals(result, expectedResult);
    }
}
