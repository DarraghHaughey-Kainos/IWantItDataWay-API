package com.kainos.ea.db;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthDaoTest {
    DatabaseConnector databaseConnector = new DatabaseConnector();
    AuthDao authDao;
    Credential login = new Credential("UnitTestAuthDao", "UnitTestAuthDao");

    public AuthDaoTest() throws ActionFailedException {
        authDao = new AuthDao();
    }

    @Test
    void register_shouldExistInDatabase_whenNewUserRegisters() throws ActionFailedException {
        authDao.registerUser(databaseConnector.getConnection(), login);

        boolean loggedInSuccessful = authDao.validateLogin(databaseConnector.getConnection(), login);

        authDao.deleteUser(databaseConnector.getConnection(), login);

        assertTrue(loggedInSuccessful);
    }

    @Test
    void parseToken_shouldReturnClaims_whenValidTokenProvided() throws ActionFailedException, AuthenticationException {
        String expectedResult = login.getEmail();
        String token = authDao.generateToken(expectedResult);
        Claims claims = authDao.parseToken(token);

        String result = claims.get("username").toString();

        assertEquals(expectedResult, result);
    }

    @Test
    void parseToken_shouldThrowAuthenticationException_whenInvalidTokenProvided() {
        assertThrows(AuthenticationException.class,
                () -> authDao.parseToken("InvalidTokenString"));
    }
}
