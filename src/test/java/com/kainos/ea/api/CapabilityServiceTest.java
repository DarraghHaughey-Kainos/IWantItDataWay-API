package com.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.cli.Capability;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CapabilityServiceTest {

    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    CapabilityDao capabilityDao = Mockito.mock(CapabilityDao.class);
    CapabilityService capabilityService = new CapabilityService(databaseConnector, capabilityDao);
    Connection conn;

    @Test
    void getAllCapabilities_shouldReturnCapabilities_whenDaoReturnsCapabilities() throws ActionFailedException {
        Capability capability1 = new Capability(1, "Innovation");
        Capability capability2 = new Capability(2, "Engineering");
        Capability capability3 = new Capability(3, "Data & AI");

        List<Capability> capabilities = new ArrayList<>();
        capabilities.add(capability1);
        capabilities.add(capability2);
        capabilities.add(capability3);

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(capabilityDao.getAllCapabilities(conn)).thenReturn(capabilities);

        List<Capability> result = capabilityDao.getAllCapabilities(conn);

        assertEquals(result, capabilities);
    }

    @Test
    void getCapabilities_shouldReturnSQLException_whenDaoReturnsSQLException() throws ActionFailedException {

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(capabilityDao.getAllCapabilities(conn)).thenThrow(ActionFailedException.class);

        assertThrows(ActionFailedException.class, () -> {
            capabilityService.getAllCapabilities();
        });
    }

}
