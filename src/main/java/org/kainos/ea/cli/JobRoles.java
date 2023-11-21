package org.kainos.ea.cli;

public class JobRoles {
    private String jobRoleTitle;
    private int jobRoleId;
    private String capabilityName;

    public JobRoles(int id, String jobRoleTitle, String capabilityName) {
        this.jobRoleTitle = jobRoleTitle;
        this.jobRoleId = id;
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
}
