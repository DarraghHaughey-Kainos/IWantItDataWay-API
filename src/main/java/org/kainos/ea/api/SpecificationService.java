package org.kainos.ea.api;

import org.kainos.ea.cli.Specification;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.SpecificationDao;

import java.util.List;

public class SpecificationService {

    private final DatabaseConnector databaseConnector;
    private final SpecificationDao specificationDao;

    public SpecificationService(DatabaseConnector databaseConnector, SpecificationDao specificationDao) {
        this.databaseConnector = databaseConnector;
        this.specificationDao = specificationDao;
    }

    public List<Specification> getAllSpecifications() throws ActionFailedException {
        return  specificationDao.getAllSpecifications(databaseConnector.getConnection());
    }
}
