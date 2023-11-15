package com.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class JobRoleServiceTest {
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);

    JobRoleService jobRoleService = new JobRoleService(databaseConnector, jobRoleDao);

    Connection conn;
    @Test
    void getJobRoles_shouldReturnJobRoles_whenDaoReturnsJobRoles() throws ActionFailedException {
        JobRole jobRole1 = new JobRole(1,"Testing Engineer", "Manager");
        JobRole jobRole2 = new JobRole(2,"Testing2 Engineer", "Manager");
        JobRole jobRole3 = new JobRole(3,"Testing3 Engineer", "Manager");

        List<JobRole> jobRoles = new ArrayList<>();
        jobRoles.add(jobRole1);
        jobRoles.add(jobRole2);
        jobRoles.add(jobRole3);

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoles(conn)).thenReturn(jobRoles);

        List<JobRole> result = jobRoleDao.getJobRoles(conn);

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
}
