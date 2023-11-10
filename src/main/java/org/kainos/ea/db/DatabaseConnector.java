package org.kainos.ea.db;

import org.kainos.ea.client.ActionFailedException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static Connection conn;

    public Connection getConnection() throws ActionFailedException {
        String user, password, host, name;

        try {
            if (conn!= null && !conn.isClosed()) { return  conn; }

            user = System.getenv("DB_USERNAME");
            password = System.getenv("DB_PASSWORD");
            host = System.getenv("DB_HOST");
            name = System.getenv("DB_NAME");

            if (user == null || password == null || host == null) {
                throw new IllegalArgumentException("Environment variables " +
                        "must contain DB_USERNAME, DB_PASSWORD, DB_NAME, and DB_HOST properties");
            }

            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+name+"?useSSL=false", user, password);
            return conn;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("SQL Error: DB connection problem");
        }
    }
}
