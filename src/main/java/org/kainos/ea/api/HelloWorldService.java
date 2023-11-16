package org.kainos.ea.api;

import org.kainos.ea.cli.HelloWorld;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.HelloWorldDao;
import java.sql.SQLException;
import java.util.List;

public class HelloWorldService {

    private final DatabaseConnector databaseConnector;
    private final HelloWorldDao helloWorldDao;

    public HelloWorldService(DatabaseConnector databaseConnector, HelloWorldDao helloWorldDao) {
        this.databaseConnector = databaseConnector;
        this.helloWorldDao = helloWorldDao;
    }

    public List<HelloWorld> getHelloWorld() throws ActionFailedException {

        try {
            return helloWorldDao.getHelloWorld(databaseConnector.getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get hello world");
        }
    }
}
