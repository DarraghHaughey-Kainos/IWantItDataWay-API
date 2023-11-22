package com.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.core.JobRequestValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class JobRoleServiceTest {
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);

    JobRequestValidator jobRequestValidator = Mockito.mock(JobRequestValidator.class);

    JobRoleService jobRoleService = new JobRoleService(databaseConnector, jobRoleDao, jobRequestValidator);

    Connection conn;
    @Test
    void getJobRoles_shouldReturnJobRoles_whenDaoReturnsJobRoles() throws ActionFailedException {
        JobRoles jobRoles1 = new JobRoles(1,"Testing Engineer", "Engineering", "Manager");
        JobRoles jobRoles2 = new JobRoles(2,"Testing2 Engineer", "Engineering", "Manager");
        JobRoles jobRoles3 = new JobRoles(3,"Testing3 Engineer", "Engineering", "Manager");

        List<JobRoles> jobRoles = new ArrayList<>();
        jobRoles.add(jobRoles1);
        jobRoles.add(jobRoles2);
        jobRoles.add(jobRoles3);

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoles(conn)).thenReturn(jobRoles);

        List<JobRoles> result = jobRoleService.getJobRoles();

        assertEquals(result, jobRoles);
    }

    @Test
    void getEmployees_shouldReturnSQLException_whenDaoReturnsSQLException() throws ActionFailedException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoles(conn)).thenThrow(ActionFailedException.class);

        assertThrows(ActionFailedException.class, () -> {
            jobRoleService.getJobRoles();
        });
    }

    @Test
    void getJobRoleById_shouldReturnJobRole_whenDaoReturnsJobRole() throws ActionFailedException, JobRoleDoesNotExistException {

        JobRole jobRole = new JobRole(1, "Job Role Title", "Capability Name", "www.link.com", "Specification 1, Specification 2, Specification 3", "Associate");

        List<JobRole> jobRoles = new ArrayList<>();
        jobRoles.add(jobRole);

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(conn, 1)).thenReturn(jobRoles);

        List<JobRole> result = jobRoleDao.getJobRoleById(conn, 1);

        assertEquals(result, jobRoles);
    }

    @Test
    void getJobRoleById_shouldReturnSQLException_whenDaoReturnsSQLException() throws ActionFailedException, JobRoleDoesNotExistException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(conn, 1)).thenThrow(ActionFailedException.class);

        assertThrows(ActionFailedException.class, () -> {
            jobRoleService.getJobRoleById(1);
        });
    }

    @Test
    void getJobRoleById_shouldReturnJobRoleDoesNotExistException_whenDaoReturnsJobRoleNotFoundException() throws ActionFailedException, JobRoleDoesNotExistException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(conn, 1)).thenThrow(JobRoleDoesNotExistException.class);

        assertThrows(JobRoleDoesNotExistException.class, () -> {
           jobRoleService.getJobRoleById(1);
        });
    }
}
