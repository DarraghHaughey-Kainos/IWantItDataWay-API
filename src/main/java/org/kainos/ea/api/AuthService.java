package org.kainos.ea.api;

import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;

import java.sql.SQLException;

public class AuthService {
    private  final DatabaseConnector databaseConnector;
    private final AuthDao authDao;

    public AuthService(DatabaseConnector databaseConnector, AuthDao authDao) {
        this.databaseConnector = databaseConnector;
        this.authDao = authDao;
    }

    public String register(Credential login) throws ActionFailedException {
        authDao.registerUser(databaseConnector.getConnection(), login);
        return authDao.generateToken(login.getUsername());
    }

    public String login(Credential login) throws ActionFailedException, AuthenticationException {
        if (authDao.validateLogin(databaseConnector.getConnection(), login)) {
            return authDao.generateToken(login.getUsername());
        }
        throw new AuthenticationException("The provided credentials could not be authenticated");
    }

    public void isValidToken(String token) throws AuthenticationException {
        if (token == null) {
            throw new AuthenticationException("No token provided");
        }
        authDao.parseToken(token);
    }
}
