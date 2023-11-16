package org.kainos.ea.cli;

public class JobRole {
    private String jobRoleTitle;
    private int id;
    private String capability_name;

    public JobRole(int id, String jobRoleTitle, String capability_name) {
        this.jobRoleTitle = jobRoleTitle;
        this.id = id;
        this.capability_name = capability_name;
    }

    public String getJobRoleTitle() {
        return jobRoleTitle;
    }

    public void setJobRoleTitle(String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCapability_name() {
        return capability_name;
    }

    public void setCapability_name(String capability_name) {
        this.capability_name = capability_name;
    }
}
