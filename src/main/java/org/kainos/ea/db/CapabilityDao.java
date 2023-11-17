package org.kainos.ea.db;

import org.kainos.ea.cli.Band;
import org.kainos.ea.cli.Capability;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;

import java.sql.*;
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

    public Capability getCapabilityById(Connection c, int id) throws ActionFailedException, DoesNotExistException {
        String queryString = "SELECT capability_id, capability_name " +
                "FROM capability " +
                "WHERE capability_id = ?";

        try (PreparedStatement st = c.prepareStatement(queryString)) {
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Capability(
                        rs.getInt("capability_id"),
                        rs.getString("capability_name")
                );
            }
            throw new DoesNotExistException("Capability ID does not exist");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Capability");
        }
    }
}
