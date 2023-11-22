package org.kainos.ea.core;

import org.kainos.ea.cli.JobRoleRequest;

public class JobRoleRequestValidator {

    public String isValidJobRole(JobRoleRequest jobRoleRequest) {

        if(jobRoleRequest.getJobRoleTitle().length() < 5){
            return "The job role title must be at least 5 characters";
        }

        if(jobRoleRequest.getJobRoleTitle().length() > 100){
            return "The job role title must be 100 characters or less";
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
