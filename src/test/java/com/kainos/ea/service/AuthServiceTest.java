package com.kainos.ea.service;

import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthServiceTest {
    AuthDao authDao = Mockito.mock(AuthDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    AuthService authService = new AuthService(databaseConnector, authDao);
    Connection conn;

    Credential credential = new Credential("Test", "Test");

    void registerUser_shouldReturnToken_whenDaoReturnsTrue() throws SQLException, AuthenticationException, ActionFailedException {
        String expectedResult = "test_jwt_token";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.registerUser(conn, credential)).thenReturn(expectedResult);

        String result = authService.register(credential);

        assertEquals(result, expectedResult);
    }
}
