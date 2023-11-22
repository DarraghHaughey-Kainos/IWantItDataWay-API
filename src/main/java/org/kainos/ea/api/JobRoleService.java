package org.kainos.ea.api;

import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.JobRoleRequestValidator;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import java.util.List;

public class JobRoleService {
    private final DatabaseConnector databaseConnector;
    private final JobRoleDao jobRoleDao;
    private final BandDao bandDao;
    private final CapabilityDao capabilityDao;

    private final JobRoleRequestValidator jobRoleRequestValidator = new JobRoleRequestValidator();

    public JobRoleService(DatabaseConnector databaseConnector, JobRoleDao jobRoleDao, BandDao bandDao, CapabilityDao capabilityDao) {
        this.databaseConnector = databaseConnector;
        this.jobRoleDao = jobRoleDao;
        this.bandDao = bandDao;
        this.capabilityDao = capabilityDao;
    }

    public List<JobRole> getJobRoles() throws ActionFailedException {
        return jobRoleDao.getJobRoles(databaseConnector.getConnection());
    }

    public int createJobRole(JobRoleRequest jobRoleRequest)
            throws ActionFailedException, DoesNotExistException, ValidationException {

        String error = jobRoleRequestValidator.isValidJobRole(jobRoleRequest);

        if(error != null){
            throw new ValidationException(error);
        }

        return jobRoleDao.createJobRole(databaseConnector.getConnection(), jobRoleRequest);
    }
}
