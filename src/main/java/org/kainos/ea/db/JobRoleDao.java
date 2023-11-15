package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<JobRole> getJobRoles(Connection c) throws ActionFailedException {
        try (Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT job_role_id, job_role_title FROM job_role;");

            List<JobRole> jobRoleList = new ArrayList<>();

            while (rs.next()) {
                JobRole jobRole = new JobRole(
                        rs.getInt("job_role_id"),
                        rs.getString("job_role_title")
                );

                jobRoleList.add(jobRole);
            }

            return jobRoleList;
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Job Roles");
        }

    }

    public int createJobRoles(JobRoleRequest jobRoleRequest) throws ActionFailedException {

        try{

            Connection c = databaseConnector.getConnection();

            String insertStatement = "INSERT INTO job_role (job_role_title, capability_id) VALUES (?, ?);";

            PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, jobRoleRequest.getJobRoleTitle());
            st.setInt(2, jobRoleRequest.getCapability_id());

            st.executeUpdate();

            ResultSet resultSet = st.getGeneratedKeys();

            if(!resultSet.next()){

                throw new ActionFailedException("Failed to create job role " + jobRoleRequest);

            }

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}