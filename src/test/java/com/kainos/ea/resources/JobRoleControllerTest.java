package com.kainos.ea.resources;

import org.junit.jupiter.api.Test;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.resources.JobRoleController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRoleControllerTest {
    JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);
    JobRoleController jobRoleController = new JobRoleController(jobRoleService);

    @Test
    void jobRoleController_shouldReturn500Response_whenJobRoleServiceThrowsActionFailedException() throws ActionFailedException {
        int expectedStatusCode = 500;

        Mockito.doThrow(ActionFailedException.class).when(jobRoleService).getJobRoles();

        Response response = jobRoleController.getJobRoles();

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void jobRoleController_shouldReturn200Response_whenJobRoleServiceDoesNotThrowException() throws ActionFailedException {
        int expectedStatusCode = 200;

        JobRole jobRole1 = new JobRole(1,"Testing Engineer", "Manager");
        JobRole jobRole2 = new JobRole(2,"Testing2 Engineer", "Manager");
        JobRole jobRole3 = new JobRole(3,"Testing3 Engineer", "Manager");

        List<JobRole> jobRoles = new ArrayList<>();
        jobRoles.add(jobRole1);
        jobRoles.add(jobRole2);
        jobRoles.add(jobRole3);

        Mockito.when(jobRoleService.getJobRoles()).thenReturn(jobRoles);

        Response response = jobRoleController.getJobRoles();

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), jobRoles);
    }
}
