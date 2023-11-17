package org.kainos.ea.core;

import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ValidationException;

import java.util.regex.Pattern;

public class CredentialValidator {

    private final String emailValidationRegex = "^(.+)@(.+)$";
    Pattern pattern = Pattern.compile(emailValidationRegex);

    public void isValidUser(Credential credential) throws ValidationException {

        if (credential.getEmail().length() > 255) {
            throw new ValidationException("The email address provided is too long");
        }

        if (!pattern.matcher(credential.getEmail()).matches()) {
            throw new ValidationException("The email address is not in the correct format");
        }

    }
}
