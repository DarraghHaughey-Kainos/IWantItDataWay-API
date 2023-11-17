package org.kainos.ea.db;

import org.kainos.ea.cli.Band;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BandDao {

    public List<Band> getBands(Connection c) throws ActionFailedException {
        try (Statement st = c.createStatement()) {
            String queryString = "SELECT band_id, band_name " +
                    "FROM band;";

            ResultSet rs = st.executeQuery(queryString);

            List<Band> bandList = new ArrayList<>();

            while (rs.next()) {
                Band band = new Band(
                        rs.getInt("band_id"),
                        rs.getString("band_name")
                );

                bandList.add(band);
            }

            return bandList;
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Bands");
        }
    }

    public Band getBandById(Connection c, int id) throws ActionFailedException, DoesNotExistException {
        String queryString = "SELECT band_id, band_name " +
                "FROM band " +
                "WHERE band_id = ?";

        try (PreparedStatement st = c.prepareStatement(queryString)) {
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Band(
                        rs.getInt("band_id"),
                        rs.getString("band_name")
                );
            }
            throw new DoesNotExistException("Band ID does not exist");
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Bands");
        }
    }
}
