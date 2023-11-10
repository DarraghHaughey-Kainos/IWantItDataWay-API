package com.kainos.ea.service;

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
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    AuthDao authDao = Mockito.mock(AuthDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    AuthService authService = new AuthService(databaseConnector, authDao);
    Connection conn;

    Credential login = new Credential("Test", "Test");

    @Test
    void registerUser_shouldReturnToken_whenDaoReturnsTrue() throws ActionFailedException {
        String expectedResult = "test_jwt_token";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.generateToken(login.getUsername())).thenReturn(expectedResult);

        String result = authService.register(login);

        assertEquals(result, expectedResult);
    }

    @Test
    void registerUser_shouldThrowAuthenticationException_whenDaoReturnsFalse() throws ActionFailedException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.doThrow(ActionFailedException.class).when(authDao).registerUser(conn, login);

        assertThrows(ActionFailedException.class,
                () -> authService.register(login));
    }
}
