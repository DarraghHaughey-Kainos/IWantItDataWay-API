package com.kainos.ea.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.CredentialValidator;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CredentialValidatorTest {

    CredentialValidator credentialValidator = new CredentialValidator();
    Credential validLogin = new Credential("test@test", "Test");
    Credential invalidLogin = new Credential("test", "Test");

    @Test
    void isValidUser_shouldNotThrowException_whenValidLoginProvided() {
        assertDoesNotThrow(() -> credentialValidator.isValidUser(validLogin));
    }

    @Test
    void isValidUser_shouldThrowValidationException_whenInvalidLoginProvided() {
        assertThrows(ValidationException.class,
                () -> credentialValidator.isValidUser(invalidLogin));
    }
}
