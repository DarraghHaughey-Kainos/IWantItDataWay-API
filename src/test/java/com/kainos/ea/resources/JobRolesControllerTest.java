package com.kainos.ea.resources;

import org.junit.jupiter.api.Test;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.resources.JobRoleController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRolesControllerTest {
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

        JobRoles jobRoles1 = new JobRoles(1,"Testing Engineer", "Engineering");
        JobRoles jobRoles2 = new JobRoles(2,"Testing2 Engineer", "Engineering");
        JobRoles jobRoles3 = new JobRoles(3,"Testing3 Engineer", "Engineering");

        List<JobRoles> jobRoles = new ArrayList<>();
        jobRoles.add(jobRoles1);
        jobRoles.add(jobRoles2);
        jobRoles.add(jobRoles3);

        Mockito.when(jobRoleService.getJobRoles()).thenReturn(jobRoles);

        Response response = jobRoleController.getJobRoles();

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), jobRoles);
    }
}
