package org.kainos.ea.api;

import org.kainos.ea.cli.Capability;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;

import java.util.List;

public class CapabilityService {

    private final DatabaseConnector databaseConnector;
    private final CapabilityDao capabilityDao;

    public CapabilityService(DatabaseConnector databaseConnector, CapabilityDao capabilityDao) {
        this.databaseConnector = databaseConnector;
        this.capabilityDao = capabilityDao;
    }

    public List<Capability> getAllCapabilities() throws ActionFailedException {
        return capabilityDao.getAllCapabilities(databaseConnector.getConnection());
    }

}
