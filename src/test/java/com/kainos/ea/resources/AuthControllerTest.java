package com.kainos.ea.resources;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.resources.AuthController;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    AuthService authService = Mockito.mock(AuthService.class);
    AuthController authController = new AuthController(authService);

    Credential login = new Credential("TestUser2", "Test");

    @Test
    void register_shouldReturn200AndToken_whenServiceDoesNotThrowException() throws ActionFailedException, ValidationException {
        String expectedResult = "test_jwt_token";
        int expectedStatusCode = 200;

        Mockito.when(authService.register(login)).thenReturn(expectedResult);

        Response response = authController.register(login);

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), expectedResult);
    }

    @Test
    void register_shouldReturn400Response_whenValidatorThrowsValidationException() throws ActionFailedException, ValidationException {
        int expectedStatusCode = 400;

        Mockito.doThrow(ValidationException.class).when(authService).register(login);

        Response response = authController.register(login);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void register_shouldReturn500Response_whenDaoThrowsActionFailedException() throws ActionFailedException, ValidationException {
        int expectedStatusCode = 500;

        Mockito.doThrow(ActionFailedException.class).when(authService).register(login);

        Response response = authController.register(login);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void login_shouldReturn200AndToken_whenServiceDoesNotThrowException() throws ActionFailedException, AuthenticationException {
        String expectedResult = "test_jwt_token";
        int expectedStatusCode = 200;

        Mockito.when(authService.login(login)).thenReturn(expectedResult);

        Response response = authController.login(login);

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), expectedResult);
    }

    @Test
    void login_shouldReturn401Response_whenDaoThrowsAuthenticationException() throws ActionFailedException, AuthenticationException {
        int expectedStatusCode = 401;

        Mockito.doThrow(AuthenticationException.class).when(authService).login(login);

        Response response = authController.login(login);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void login_shouldReturn500Response_whenDaoThrowsActionFailedException() throws ActionFailedException, AuthenticationException {
        int expectedStatusCode = 500;

        Mockito.doThrow(ActionFailedException.class).when(authService).login(login);

        Response response = authController.login(login);

        assertEquals(response.getStatus(), expectedStatusCode);
    }
}
