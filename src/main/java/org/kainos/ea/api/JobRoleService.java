package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
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

    public int createJobRole(JobRoleRequest jobRoleRequest) throws ActionFailedException {
        //TODO: Add validator
        return jobRoleDao.createJobRole(databaseConnector.getConnection(), jobRoleRequest);
    }
}
