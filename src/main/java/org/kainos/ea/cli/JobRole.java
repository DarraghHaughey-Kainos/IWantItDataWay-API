package org.kainos.ea.cli;

public class JobRole {
    private String jobRoleTitle;
    private int id;

    public JobRole(int id, String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
        this.id = id;
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
}
