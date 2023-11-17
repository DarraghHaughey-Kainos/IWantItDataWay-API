package com.kainos.ea.cli;

import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.Credential;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CredentialTest {

    @Test
    void getterAndSetterCredentialTest() {
        Credential credential = new Credential("test@example.com", "password123");

        String email = "newemail@example.com";
        String password = "newpassword456";

        credential.setEmail(email);
        credential.setPassword(password);

        assertEquals(credential.getEmail(), email);
        assertEquals(credential.getPassword(), password);
    }
}
