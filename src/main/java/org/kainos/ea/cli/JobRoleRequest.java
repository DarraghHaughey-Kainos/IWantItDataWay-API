package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRoleRequest {
    private String jobRoleTitle;
    private int bandId;
    private int capabilityId;
    private String sharepointLink;

    public JobRoleRequest(
            @JsonProperty("jobRoleTitle") String jobRoleTitle,
            @JsonProperty("bandId") int bandId,
            @JsonProperty("capabilityId") int capabilityId,
            @JsonProperty("sharepointLink") String sharepointLink) {
        this.jobRoleTitle = jobRoleTitle;
        this.bandId = bandId;
        this.capabilityId = capabilityId;
        this.sharepointLink = sharepointLink;
    }

    public String getJobRoleTitle() {
        return jobRoleTitle;
    }

    public void setJobRoleTitle(String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public int getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(int capabilityId) {
        this.capabilityId = capabilityId;
    }

    public String getSharepointLink() {
        return sharepointLink;
    }

    public void setSharepointLink(String sharepointLink) {
        this.sharepointLink = sharepointLink;
    }
}
