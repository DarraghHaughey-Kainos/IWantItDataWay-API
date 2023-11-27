package com.kainos.ea.resources;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.resources.JobRoleController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRoleControllerTest {
    JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);
    AuthService authService = Mockito.mock(AuthService.class);
    JobRoleController jobRoleController = new JobRoleController(jobRoleService, authService);

    @Test
    void jobRoleController_shouldReturn500Response_whenJobRoleServiceThrowsActionFailedException() throws ActionFailedException, AuthenticationException {
        int expectedStatusCode = 500;
        String token = "";
        String permission = "View";
        Claims claims = new DefaultClaims();

        Mockito.doThrow(ActionFailedException.class).when(jobRoleService).getJobRoles();
        Mockito.when(authService.isValidToken(token, permission)).thenReturn(claims);

        Response response = jobRoleController.getJobRoles(token);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn200Response_whenJobRoleServiceDoesNotThrowException() throws ActionFailedException, AuthenticationException {
        int expectedStatusCode = 200;

        JobRoles jobRole1 = new JobRoles(1, "Job Role Title", "Capability Name", "Associate");
        JobRoles jobRole2 = new JobRoles(2, "Job Role Title", "Capability Name", "Associate");
        JobRoles jobRole3 = new JobRoles(3, "Job Role Title", "Capability Name", "Associate");

        List<JobRoles> jobRoles = new ArrayList<>();
        jobRoles.add(jobRole1);
        jobRoles.add(jobRole2);
        jobRoles.add(jobRole3);


        Mockito.when(jobRoleService.getJobRoles()).thenReturn(jobRoles);

        Response response = jobRoleController.getJobRoles("");

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), jobRoles);
    }
}
