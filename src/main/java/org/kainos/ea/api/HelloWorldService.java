package org.kainos.ea.api;

import org.kainos.ea.cli.HelloWorld;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.HelloWorldDao;

import java.sql.SQLException;
import java.util.List;

public class HelloWorldService {
    private final HelloWorldDao helloWorldDao = new HelloWorldDao();

    public List<HelloWorld> getHelloWorld() throws ActionFailedException {

        try {
            return helloWorldDao.getHelloWorld();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get hello world");
        }
    }
}
