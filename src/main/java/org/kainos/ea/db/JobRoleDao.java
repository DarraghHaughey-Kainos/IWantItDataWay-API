package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleSpecification;
import org.kainos.ea.client.ActionFailedException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

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

    public List<JobRoleSpecification> getJobRole(Connection c, int id) throws ActionFailedException {

        String jobRoleQuery = "SELECT job_role_id, job_role_title, job_role_sharepoint_link " +
                "FROM job_role " +
                "WHERE job_role_id = ?;";

        String jobSpecQuery = "SELECT specification_text " +
                "FROM job_role_specification " +
                "LEFT JOIN specification s USING (specification_id) " +
                "WHERE job_role_id = ?;";

        try (PreparedStatement jobRoleStatement = c.prepareStatement(jobRoleQuery);
             PreparedStatement jobSpecStatement = c.prepareStatement(jobSpecQuery)) {

            jobRoleStatement.setInt(1, id);
            jobSpecStatement.setInt(1, id);

            ResultSet jobRoleResults = jobRoleStatement.executeQuery();
            ResultSet jobSpecResults = jobSpecStatement.executeQuery();

            List<JobRoleSpecification> jobRoleSpecificationList = new ArrayList<>();

            while (jobRoleResults.next()) {
                List<String> jobSpecList = new ArrayList<>();
                while (jobSpecResults.next()) {
                    jobSpecList.add(jobSpecResults.getString("specification_text"));
                }
                JobRoleSpecification jobRoleSpecification = new JobRoleSpecification(
                        jobRoleResults.getInt("job_role_id"),
                        jobRoleResults.getString("job_role_title"),
                        jobRoleResults.getString("job_role_sharepoint_link"),
                        jobSpecList
                );

                jobRoleSpecificationList.add(jobRoleSpecification);
            }

            return jobRoleSpecificationList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Job Role Specification");
        }
    }
}