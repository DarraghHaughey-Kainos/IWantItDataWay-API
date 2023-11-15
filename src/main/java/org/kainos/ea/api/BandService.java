package org.kainos.ea.api;

import org.kainos.ea.cli.Band;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.db.DatabaseConnector;

import java.util.List;

public class BandService {
    private final DatabaseConnector databaseConnector;
    private final BandDao bandDao;

    public BandService(DatabaseConnector databaseConnector, BandDao bandDao) {
        this.databaseConnector = databaseConnector;
        this.bandDao = bandDao;
    }

    public List<Band> getBands() throws ActionFailedException {
        return bandDao.getBands(databaseConnector.getConnection());
    }
}
