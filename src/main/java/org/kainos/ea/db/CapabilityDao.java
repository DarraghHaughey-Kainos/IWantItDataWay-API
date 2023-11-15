package org.kainos.ea.db;

import org.kainos.ea.cli.Capability;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.ActionFailedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CapabilityDao {


    public List<Capability> getAllCapabilities(Connection c) throws ActionFailedException{

        try(Statement st = c.createStatement()){
            ResultSet rs = st.executeQuery("SELECT capability_id, capability_name FROM capability;");

            List<Capability> capabilityList = new ArrayList<>();

            while(rs.next()) {

                Capability capability = new Capability(
                        rs.getInt("capability_id"),
                        rs.getString("capability_name")
                );

                capabilityList.add(capability);

            }

            return capabilityList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Capabilities");
        }

    }

}
