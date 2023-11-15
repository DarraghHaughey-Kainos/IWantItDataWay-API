package org.kainos.ea.cli;

public class JobRoleRequest {
    private String jobRoleTitle;
    private int capability_id;

    public JobRoleRequest(String jobRoleTitle, int capability_id) {
        this.jobRoleTitle = jobRoleTitle;
        this.capability_id=capability_id;
    }

    public String getJobRoleTitle() {
        return jobRoleTitle;
    }

    public void setJobRoleTitle(String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
    }

    public int getCapability_id() {
        return capability_id;
    }

    public void setCapability_id(int capability_id) {
        this.capability_id = capability_id;
    }
}
