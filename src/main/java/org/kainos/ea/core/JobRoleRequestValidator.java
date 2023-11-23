package org.kainos.ea.core;

import org.kainos.ea.cli.Band;
import org.kainos.ea.cli.Capability;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;
import org.kainos.ea.db.BandDao;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;

public class JobRoleRequestValidator {

    BandDao bandDao = new BandDao();
    CapabilityDao capabilityDao = new CapabilityDao();

    DatabaseConnector databaseConnector = new DatabaseConnector();

    public String isValidJobRole(JobRoleRequest jobRoleRequest) throws ActionFailedException, DoesNotExistException {

        Band band = bandDao.getBandById(databaseConnector.getConnection(), jobRoleRequest.getBandId());
        Capability capability = capabilityDao.getCapabilityById(databaseConnector.getConnection(), jobRoleRequest.getCapabilityId());

        if(jobRoleRequest.getJobRoleTitle().length() < 5){
            return "The job role title must be at least 5 characters";
        }

        if(jobRoleRequest.getJobRoleTitle().length() > 100){
            return "The job role title must be 100 characters or less";
        }

        if(band == null){
            return "You must choose a valid band";
        }

        if(capability == null){
            return "You must choose a valid capability";
        }

        if(jobRoleRequest.getSharepointLink().length() < 15){
            return "Sharepoint Link must be at least 15 characters to be a valid link";
        }

        if(jobRoleRequest.getSharepointLink().length() > 500){
            return "Sharepoint Links must be 500 characters or less to be a valid link";
        }

        return null;
    }

}
