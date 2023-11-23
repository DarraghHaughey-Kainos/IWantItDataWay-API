package com.kainos.ea.resources;

import org.junit.jupiter.api.Test;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.AuthenticationException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.resources.JobRoleController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRolesControllerTest {
    JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);
    AuthService authService = Mockito.mock(AuthService.class);
    JobRoleController jobRoleController = new JobRoleController(jobRoleService, authService);

    @Test
    void jobRoleController_shouldReturn500Response_whenJobRoleServiceThrowsActionFailedException() throws ActionFailedException {
        int expectedStatusCode = 500;

        Mockito.doThrow(ActionFailedException.class).when(jobRoleService).getJobRoles();

        Response response = jobRoleController.getJobRoles("");

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn200Response_whenJobRoleServiceDoesNotThrowException() throws ActionFailedException {
        int expectedStatusCode = 200;

        JobRoles jobRoles1 = new JobRoles(1,"Testing Engineer", "Engineering", "Manager");
        JobRoles jobRoles2 = new JobRoles(2,"Testing2 Engineer", "Engineering", "Manager");
        JobRoles jobRoles3 = new JobRoles(3,"Testing3 Engineer", "Engineering", "Manager");

        List<JobRoles> jobRoles = new ArrayList<>();
        jobRoles.add(jobRoles1);
        jobRoles.add(jobRoles2);
        jobRoles.add(jobRoles3);

        Mockito.when(jobRoleService.getJobRoles()).thenReturn(jobRoles);

        Response response = jobRoleController.getJobRoles("");

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), jobRoles);
    }

    @Test
    void jobRoleController_shouldReturn500Response_whenJobRoleServiceGetJobByIdThrowsActionFailedException() throws ActionFailedException, JobRoleDoesNotExistException {
        int expectedStatusCode = 500;

        Mockito.doThrow(ActionFailedException.class).when(jobRoleService).getJobRoleById(1);

        Response response = jobRoleController.getJobRoleById("", 1);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn404Response_whenJobRoleServiceGetJobByIdThrowsJobRoleDoesNotExistException() throws ActionFailedException, JobRoleDoesNotExistException {
        int expectedStatusCode = 404;

        Mockito.doThrow(JobRoleDoesNotExistException.class).when(jobRoleService).getJobRoleById(1);

        Response response = jobRoleController.getJobRoleById("", 1);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn200Response_whenJobRoleServiceGetJobRoleByIdDoesNotThrowException() throws ActionFailedException, JobRoleDoesNotExistException {
        int expectedStatusCode = 200;

        JobRole jobRole = new JobRole(1, "Testing Engineer", "Engineering", "www.link.com", "Spec 1, Spec 2, Spec 3", "Associate");

        List<JobRole> jobRoleList = new ArrayList<>();
        jobRoleList.add(jobRole);

        Mockito.when(jobRoleService.getJobRoleById(1)).thenReturn(jobRoleList);

        Response response = jobRoleController.getJobRoleById("", 1);

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), jobRoleList);
    }

    @Test
    void jobRoleController_shouldReturn204Response_whenJobRoleServiceDeleteJobRoleByIdIsCalledWithValidId() throws ActionFailedException, JobRoleDoesNotExistException, ValidationException {
        int expectedStatusCode = 204;
        int id = 1;

        Mockito.when(jobRoleService.deleteJobRoleById(id)).thenReturn("Job Role with '" + id + "' has been removed!");

        Response response = jobRoleController.deleteJobRoleById("", id);

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), "Job Role with '" + id + "' has been removed!");
    }

    @Test
    void jobRoleController_shouldReturn403Response_whenJobRoleServiceDeleteJobRoleByIdIsCalledWithUnauthorisedToken() throws ActionFailedException, JobRoleDoesNotExistException, ValidationException, AuthenticationException {
        int expectedStatusCode = 403;
        int id = 1;

        Mockito.when(authService.isValidToken("", "Admin")).thenThrow(AuthenticationException.class);

        Response response = jobRoleController.deleteJobRoleById("", id);
        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn404Response_whenJobRoleServiceDeleteJobRoleByIdIsCalledWithInValidId() throws ActionFailedException, JobRoleDoesNotExistException, ValidationException, AuthenticationException {
        int expectedStatusCode = 404;
        int id = -1;

        Mockito.when(jobRoleService.deleteJobRoleById(id)).thenThrow(JobRoleDoesNotExistException.class);

        Response response = jobRoleController.deleteJobRoleById("", id);
        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn500Response_whenServerErrorOccurs() throws ActionFailedException, JobRoleDoesNotExistException, ValidationException, AuthenticationException {
        int expectedStatusCode = 500;
        int id = 1;

        Mockito.when(jobRoleService.deleteJobRoleById(id)).thenThrow(ActionFailedException.class);

        Response response = jobRoleController.deleteJobRoleById("", id);
        assertEquals(response.getStatus(), expectedStatusCode);
    }
}
