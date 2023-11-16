package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.ActionFailedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<JobRole> getJobRoles(Connection c) throws ActionFailedException {
        try (Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT job_role_id, job_role_title, capability_name "
                    + "FROM job_role "
                    + "LEFT JOIN capability "
                    + "USING(capability_id);");

            List<JobRole> jobRoleList = new ArrayList<>();

            while (rs.next()) {
                JobRole jobRole = new JobRole(
                        rs.getInt("job_role_id"),
                        rs.getString("job_role_title"),
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

}