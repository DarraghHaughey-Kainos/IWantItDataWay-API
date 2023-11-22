package org.kainos.ea.core;

import org.kainos.ea.cli.JobRoleRequest;

public class JobRoleRequestValidator {

    public String isValidJobRole(JobRoleRequest jobRoleRequest) {

        if(jobRoleRequest.getJobRoleTitle().length() < 5){
            return "The job role title must be at least 5 characters";
        }

        if(jobRoleRequest.getBandId() < 1){
            return "You must choose a valid band";
        }

        if(jobRoleRequest.getCapabilityId() < 1){
            return "You must choose a valid capability";
        }

        if(jobRoleRequest.getSharepointLink().length() < 15){
            return "Sharepoint Link must be at least 15 characters to be a valid link";
        }

        return null;
    }

}
