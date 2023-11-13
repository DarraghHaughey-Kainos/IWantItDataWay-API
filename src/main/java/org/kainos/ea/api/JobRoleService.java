package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    private final DatabaseConnector databaseConnector;
    private final JobRoleDao jobRoleDao;

    public JobRoleService(DatabaseConnector databaseConnector, JobRoleDao jobRoleDao) {
        this.databaseConnector = databaseConnector;
        this.jobRoleDao = jobRoleDao;
    }

    public List<JobRole> getJobRoles() throws ActionFailedException {
        return jobRoleDao.getJobRoles(databaseConnector.getConnection());
    }
}
