package org.kainos.ea.db;

import org.kainos.ea.cli.JobRole;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getJobRoles(Connection c) throws SQLException  {
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
           throw new SQLException("Failed to get Job Roles");
        }

    }
}