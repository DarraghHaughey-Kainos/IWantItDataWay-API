package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
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

    public List<JobRoles> getJobRoles() throws ActionFailedException {
        return jobRoleDao.getJobRoles(databaseConnector.getConnection());
    }

    public List<JobRole> getJobRoleById(int id) throws ActionFailedException, JobRoleDoesNotExistException {
        List<JobRole> jobRole = jobRoleDao.getJobRoleById(databaseConnector.getConnection(), id);

        if (jobRole.isEmpty()) {
            throw new JobRoleDoesNotExistException("Job role with ID " + id + " does not exist");
        }
        return jobRole;
    }

}
