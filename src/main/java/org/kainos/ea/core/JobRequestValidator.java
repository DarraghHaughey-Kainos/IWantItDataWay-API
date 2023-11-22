package org.kainos.ea.core;

import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;

import java.util.ArrayList;
import java.util.List;

public class JobRequestValidator {
    public void checkJobRoleIdExists (DatabaseConnector databaseConnector, JobRoleDao jobRoleDao, int idToCheck)
            throws ActionFailedException, ValidationException {

        List<Integer> jobRoleIdsList = new ArrayList<Integer>();
        List<JobRoles> jobRolesList = jobRoleDao.getJobRoles(databaseConnector.getConnection());

        jobRolesList.forEach(job -> {
            jobRoleIdsList.add(job.getJobRoleId());
        });

        if (!jobRoleIdsList.contains(idToCheck)) {
            throw new ValidationException("Job Role Id Does Not Exist!");
        }
    }
}
