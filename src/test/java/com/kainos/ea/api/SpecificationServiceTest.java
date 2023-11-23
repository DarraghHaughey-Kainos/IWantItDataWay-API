package com.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.SpecificationService;
import org.kainos.ea.cli.Specification;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.SpecificationDao;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SpecificationServiceTest {

    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    SpecificationDao specificationDao = Mockito.mock(SpecificationDao.class);
    SpecificationService specificationService = new SpecificationService(databaseConnector, specificationDao);
    Connection conn;

    @Test
    void getAllSpecifications_shouldReturnAllSpecifications_whenDaoReturnsSpecifications() throws ActionFailedException {
        Specification specification1 = new Specification(1, "Specification 1");
        Specification specification2 = new Specification(2, "Specification 2");
        Specification specification3 = new Specification(3, "Specification 3");

        List<Specification> specifications = new ArrayList<>();
        specifications.add(specification1);
        specifications.add(specification2);
        specifications.add(specification3);

        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(specificationDao.getAllSpecifications(conn)).thenReturn(specifications);

        List<Specification> result = specificationDao.getAllSpecifications(conn);

        assertEquals(result, specifications);
    }

    @Test
    void getAllSpecifications_shouldReturnSQLException_whenDaoReturnsSQLException() throws ActionFailedException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(specificationDao.getAllSpecifications(conn)).thenThrow(ActionFailedException.class);

        assertThrows(ActionFailedException.class, () -> {
            specificationService.getAllSpecifications();
        });
    }
}
