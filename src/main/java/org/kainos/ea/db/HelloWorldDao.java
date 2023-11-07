package org.kainos.ea.db;

import org.kainos.ea.cli.HelloWorld;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HelloWorldDao {

    public List<HelloWorld> getHelloWorld() throws SQLException {
        Connection c = DatabaseConnector.getConnection();
        assert c != null;

        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, name FROM hello_world;");

        List<HelloWorld> helloWorldList = new ArrayList<>();

        while (rs.next()) {
            HelloWorld helloWorld = new HelloWorld(
                    rs.getInt("id"),
                    rs.getString("name")
            );

            helloWorldList.add(helloWorld);
        }

        return helloWorldList;
    }
}
