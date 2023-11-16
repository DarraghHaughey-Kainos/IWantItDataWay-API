package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRoleRequest {
    private String jobRoleTitle;
    private int capabilityId;
    private int bandId;

    public JobRoleRequest(
            @JsonProperty("jobRoleTitle") String jobRoleTitle,
            @JsonProperty("capabilityId") int capabilityId,
            @JsonProperty("bandId") int bandId) {
        this.jobRoleTitle = jobRoleTitle;
        this.capabilityId = capabilityId;
        this.bandId = bandId;
    }

    public String getJobRoleTitle() {
        return jobRoleTitle;
    }

    public void setJobRoleTitle(String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
    }

    public int getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(int capabilityId) {
        this.capabilityId = capabilityId;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }
}
