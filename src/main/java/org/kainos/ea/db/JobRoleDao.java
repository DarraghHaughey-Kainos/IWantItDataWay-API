package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getJobRoles(Connection c) throws ActionFailedException {
        try (Statement st = c.createStatement()) {
            String queryString = "SELECT job_role_id, job_role_title, band_name " +
                    "FROM job_role " +
                    "LEFT JOIN band USING(band_id);";

            ResultSet rs = st.executeQuery(queryString);

            List<JobRole> jobRoleList = new ArrayList<>();

            while (rs.next()) {
                JobRole jobRole = new JobRole(
                        rs.getInt("job_role_id"),
                        rs.getString("job_role_title"),
                        rs.getString("band_name")
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
        try (Statement st = c.createStatement()) {
            String queryString = "INSERT INTO job_role (job_role_id, job_role_title, capability_id, band_id, job_role_sharepoint_link) " +
            "VALUES (?, ?, ?, ?, ?);";

            ResultSet rs = st.executeQuery(queryString);

            while (rs.next()) {
                JobRole jobRole = new JobRole(
                        rs.getInt("job_role_id"),
                        rs.getString("job_role_title"),
                        rs.getString("band_name")
                );

                jobRoleList.add(jobRole);
            }

            return jobRoleList;
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Job Roles");
        }
    }
}