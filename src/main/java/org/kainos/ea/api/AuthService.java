package org.kainos.ea.api;

import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.db.AuthDao;

public class AuthService {
    private final AuthDao authDao;

    public AuthService(AuthDao authDao) {
        this.authDao = authDao;
    }

    public String login(Credential login) throws ActionFailedException, AuthenticationException {
        if (authDao.validateLogin(login)) {
            return authDao.generateToken(login.getUsername());
        }
        throw new AuthenticationException("The provided credentials could not be authenticated");
    }

    public String register(Credential login) throws AuthenticationException, ActionFailedException {
        if (authDao.registerUser(login)) {
            return authDao.generateToken(login.getUsername());
        }
        throw new AuthenticationException("The account could not be created");
    }

    public void isValidToken(String token) throws AuthenticationException {
        if (token == null) {
            throw new AuthenticationException("No token provided");
        }
        authDao.parseToken(token);
    }
}
