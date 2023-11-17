package org.kainos.ea.cli;

public class JobRole {
    private int jobRoleId;
    private String jobRoleTitle;
    private String bandName;
    private String capabilityName;

    public JobRole(int jobRoleId, String jobRoleTitle, String bandName, String capabilityName) {
        this.jobRoleId = jobRoleId;
        this.jobRoleTitle = jobRoleTitle;
        this.bandName = bandName;
        this.capabilityName = capabilityName;
    }

    public String getJobRoleTitle() {
        return jobRoleTitle;
    }

    public void setJobRoleTitle(String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
    }

    public int getJobRoleId() {
        return jobRoleId;
    }

    public void setJobRoleId(int jobRoleId) {
        this.jobRoleId = jobRoleId;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public void setCapabilityName(String capabilityName) {
        this.capabilityName = capabilityName;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
}
