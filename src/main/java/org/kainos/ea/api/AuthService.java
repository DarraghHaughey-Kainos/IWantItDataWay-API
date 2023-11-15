package org.kainos.ea.api;

import io.jsonwebtoken.Claims;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.CredentialValidator;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;

public class AuthService {
    private  final DatabaseConnector databaseConnector;
    private final AuthDao authDao;
    CredentialValidator credentialValidator = new CredentialValidator();

    public AuthService(DatabaseConnector databaseConnector, AuthDao authDao, CredentialValidator credentialValidator) {
        this.databaseConnector = databaseConnector;
        this.authDao = authDao;
        this.credentialValidator = credentialValidator;
    }

    public String register(Credential login) throws ValidationException, ActionFailedException {
        credentialValidator.isValidUser(login);

        authDao.registerUser(databaseConnector.getConnection(), login);
        return authDao.generateToken(login.getEmail());
    }

    public String login(Credential login) throws ActionFailedException, AuthenticationException {
        if (authDao.validateLogin(databaseConnector.getConnection(), login)) {
            return authDao.generateToken(login.getEmail());
        } else {
            throw new AuthenticationException("The provided credentials could not be authenticated");
        }
    }

    public Claims isValidToken(String token) throws AuthenticationException {
        if (token == null) {
            throw new AuthenticationException("No token provided");
        }
        return authDao.parseToken(token);
    }
}
