package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.JobRoleDoesNotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRoles> getJobRoles(Connection c) throws ActionFailedException {
        try (Statement st = c.createStatement()) {
            String queryString = "SELECT job_role_id, job_role_title, capability_name, band_name " +
                    "FROM job_role "
                    + "LEFT JOIN capability "
                    + "USING(capability_id)"
                    + "LEFT JOIN band USING(band_id);";

            ResultSet rs = st.executeQuery(queryString);

            List<JobRoles> jobRolesList = new ArrayList<>();

            while (rs.next()) {
                JobRoles jobRoles = new JobRoles(
                        rs.getInt("job_role_id"),
                        rs.getString("job_role_title"),
                        rs.getString("capability_name"),
                        rs.getString("band_name")
                        );

                jobRolesList.add(jobRoles);
            }

            return jobRolesList;
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Job Roles");
        }

    }

    public List<JobRole> getJobRoleById(Connection c, int id) throws ActionFailedException, JobRoleDoesNotExistException {

        String query = "SELECT job_role.job_role_id, job_role.job_role_title, job_role.job_role_sharepoint_link, capability.capability_name, band.band_name, " +
                "GROUP_CONCAT(specification.specification_text SEPARATOR ', ') AS job_role_specs " +
                "FROM job_role " +
                "LEFT JOIN capability ON job_role.capability_id = capability.capability_id " +
                "LEFT JOIN band ON job_role.band_id = band.band_id " +
                "LEFT JOIN job_role_specification ON job_role.job_role_id = job_role_specification.job_role_id " +
                "LEFT JOIN specification ON job_role_specification.specification_id = specification.specification_id " +
                "WHERE job_role.job_role_id = ?;";


        try (PreparedStatement st = c.prepareStatement(query)) {

            st.setInt(1, id);

            ResultSet jobRoleResults = st.executeQuery();

            List<JobRole> jobRoleList = new ArrayList<>();

            while (jobRoleResults.next()) {

                JobRole jobRole = new JobRole(
                        jobRoleResults.getInt("job_role_id"),
                        jobRoleResults.getString("job_role_title"),
                        jobRoleResults.getString("capability_name"),
                        jobRoleResults.getString("job_role_sharepoint_link"),
                        jobRoleResults.getString("job_role_specs"),
                        jobRoleResults.getString("band_name")
                );

                if (jobRole.getJobRoleTitle() != null) {
                    jobRoleList.add(jobRole);
                }
            }

            return jobRoleList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ActionFailedException("Failed to get Job Role");
        }
    }

}