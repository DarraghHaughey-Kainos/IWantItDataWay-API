package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.JobRoleDoesNotExistException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.JobRequestValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.util.List;

public class JobRoleService {
    private final DatabaseConnector databaseConnector;
    private final JobRoleDao jobRoleDao;
    private final JobRequestValidator jobRequestValidator;

    public JobRoleService(DatabaseConnector databaseConnector, JobRoleDao jobRoleDao, JobRequestValidator jobRequestValidator) {
        this.databaseConnector = databaseConnector;
        this.jobRoleDao = jobRoleDao;
        this.jobRequestValidator = jobRequestValidator;
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

    public String deleteJobRoleById(int id) throws ActionFailedException, ValidationException {
        jobRequestValidator.checkJobRoleIdExists(databaseConnector, jobRoleDao, id);
        return jobRoleDao.deleteJobRoleById(databaseConnector.getConnection(), id);
    }
}
