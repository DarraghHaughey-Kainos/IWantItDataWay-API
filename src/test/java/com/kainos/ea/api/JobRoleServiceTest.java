package com.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobRoleService;
import org.kainos.ea.cli.Band;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class JobRoleServiceTest {
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    BandDao bandDao =  Mockito.mock(BandDao.class);
    CapabilityDao capabilityDao = Mockito.mock(CapabilityDao.class);

    JobRoleService jobRoleService = new JobRoleService(databaseConnector, jobRoleDao, bandDao, capabilityDao);

    Connection conn;

    JobRoleRequest jobRoleRequest = new JobRoleRequest("Testing", 2, 1, "www.google.com");

    @Test
    void getJobRoles_shouldReturnJobRoles_whenDaoReturnsJobRoles() throws ActionFailedException {
        JobRole jobRole1 = new JobRole(1,"Testing Engineer", "Manager", "Engineering");
        JobRole jobRole2 = new JobRole(2,"Testing2 Engineer", "Manager", "Engineering");
        JobRole jobRole3 = new JobRole(3,"Testing3 Engineer", "Manager", "Engineering");

        List<JobRole> jobRoles = new ArrayList<>();
        jobRoles.add(jobRole1);
        jobRoles.add(jobRole2);
        jobRoles.add(jobRole3);

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoles(conn)).thenReturn(jobRoles);

        List<JobRole> result = jobRoleService.getJobRoles();

        assertEquals(result, jobRoles);
    }

    @Test
    void getJobRoles_shouldReturnActionFailedException_whenDaoReturnsActionFailedException() throws ActionFailedException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoles(conn)).thenThrow(ActionFailedException.class);

        assertThrows(ActionFailedException.class, () -> {
            jobRoleService.getJobRoles();
        });
    }

    @Test
    void createJobRole_shouldReturnJobRoleId_whenJobRoleServiceDoesNotThrowException() throws ActionFailedException, DoesNotExistException {
        int expectedId = 1;

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.createJobRole(conn, jobRoleRequest)).thenReturn(expectedId);

        int result = jobRoleService.createJobRole(jobRoleRequest);

        assertEquals(result, expectedId);
    }

    @Test
    void createJobRole_shouldReturnActionFailedException_whenDaoReturnsActionFailedException() throws ActionFailedException, DoesNotExistException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.createJobRole(conn, jobRoleRequest)).thenThrow(ActionFailedException.class);

        assertThrows(ActionFailedException.class, () -> {
            jobRoleService.createJobRole(jobRoleRequest);
        });
    }

    @Test
    void createJobRole_shouldReturnDoesNotExistException_whenBandDaoThrowsDoesNotExistException() throws ActionFailedException, DoesNotExistException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(bandDao.getBandById(conn, jobRoleRequest.getBandId())).thenThrow(DoesNotExistException.class);

        assertThrows(DoesNotExistException.class, () -> {
            jobRoleService.createJobRole(jobRoleRequest);
        });
    }

    @Test
    void createJobRole_shouldReturnDoesNotExistException_whenCapabilityDaoThrowsDoesNotExistException() throws ActionFailedException, DoesNotExistException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(capabilityDao.getCapabilityById(conn, jobRoleRequest.getCapabilityId())).thenThrow(DoesNotExistException.class);

        assertThrows(DoesNotExistException.class, () -> {
            jobRoleService.createJobRole(jobRoleRequest);
        });
    }
}
