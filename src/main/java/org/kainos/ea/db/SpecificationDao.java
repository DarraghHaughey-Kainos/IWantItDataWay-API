package org.kainos.ea.db;

import org.kainos.ea.cli.Specification;
import org.kainos.ea.client.ActionFailedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpecificationDao {

    public List<Specification> getAllSpecifications(Connection c) throws ActionFailedException {

        try (Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT specification_id, specification_text FROM specification;");

            List<Specification> specificationList = new ArrayList<>();

            while (rs.next()) {
                Specification specification = new Specification(
                        rs.getInt("specification_id"),
                        rs.getString("specification_text")
                );

                specificationList.add(specification);
            }

            return specificationList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Specifications");
        }
    }
}
