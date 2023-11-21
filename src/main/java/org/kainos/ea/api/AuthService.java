package org.kainos.ea.api;

import io.jsonwebtoken.Claims;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.CredentialValidator;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AuthService {
    private  final DatabaseConnector databaseConnector;
    private final AuthDao authDao;
    CredentialValidator credentialValidator;

    public AuthService(DatabaseConnector databaseConnector, AuthDao authDao, CredentialValidator credentialValidator) {
        this.databaseConnector = databaseConnector;
        this.authDao = authDao;
        this.credentialValidator = credentialValidator;
    }

    public String register(Credential login) throws ValidationException, ActionFailedException {
        credentialValidator.isValidUser(login);

        String defaultRegistrationRole = "View";

        authDao.registerUser(databaseConnector.getConnection(), login);
        return authDao.generateToken(login.getEmail(), defaultRegistrationRole);
    }

    public String login(Credential login) throws ActionFailedException, AuthenticationException {
        if (authDao.validateLogin(databaseConnector.getConnection(), login)) {
            String userRole = authDao.getUserRole(databaseConnector.getConnection(), login.getEmail());
            return authDao.generateToken(login.getEmail(), userRole);
        } else {
            throw new AuthenticationException("The provided credentials could not be authenticated");
        }
    }

    public Claims isValidToken(String token, String requiredPermission) throws AuthenticationException {
        if (token == null) {
            throw new AuthenticationException("No token provided");
        } else if (requiredPermission == null) {
            throw new AuthenticationException("No token permission set for endpoint");
        }

        Claims decodedJWT = authDao.parseToken(token);
        System.out.println(decodedJWT);
        String tokenPermission = decodedJWT.get("role").toString();

        if (Objects.equals(requiredPermission, "View")) {
            List<String> viewPermission = new ArrayList<>(Arrays.asList("View", "Admin"));
            if (!viewPermission.contains(tokenPermission)) {
                throw new AuthenticationException("User does not have permissions for this endpoint");
            }
        } else if (Objects.equals(requiredPermission, "Admin")) {
            if (!Objects.equals(tokenPermission, "Admin")) {
                throw new AuthenticationException("User does not have permissions for this endpoint");
            }
        } else {
            throw new AuthenticationException("User permissions not Specified");
        }

        return decodedJWT;
    }
}
