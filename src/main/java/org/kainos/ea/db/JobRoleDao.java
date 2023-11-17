package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getJobRoles(Connection c) throws ActionFailedException {
        try (Statement st = c.createStatement()) {
            String queryString = "SELECT job_role_id, job_role_title, band_name, capability_name " +
                    "FROM job_role " +
                    "LEFT JOIN band USING(band_id) " +
                    "LEFT JOIN capability USING(capability_id);";

            ResultSet rs = st.executeQuery(queryString);

            List<JobRole> jobRoleList = new ArrayList<>();

            while (rs.next()) {
                JobRole jobRole = new JobRole(
                        rs.getInt("job_role_id"),
                        rs.getString("job_role_title"),
                        rs.getString("band_name"),
                        rs.getString("capability_name")
                );

                jobRoleList.add(jobRole);
            }

            return jobRoleList;
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Job Roles");
        }

    }

    public int createJobRole(Connection c, JobRoleRequest jobRoleRequest) throws ActionFailedException {

        // Insert employee into employee table
        String insertEmployeeStatement = "INSERT INTO job_role (job_role_title, capability_id, band_id, job_role_sharepoint_link) "+
                "VALUES (?,?,?,?);";

        try (PreparedStatement st = c.prepareStatement(insertEmployeeStatement, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, jobRoleRequest.getJobRoleTitle());
            st.setInt(2, jobRoleRequest.getCapabilityId());
            st.setInt(3, jobRoleRequest.getBandId());
            st.setString(4, jobRoleRequest.getSharepointLink());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new ActionFailedException("Could not get Job Role id");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to create Job Role");
        }
    }
}