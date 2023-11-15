package org.kainos.ea.cli;

public class JobRole {
    private String jobRoleTitle;
    private int id;
    private String bandName;

    public JobRole(int id, String jobRoleTitle, String bandName) {
        this.jobRoleTitle = jobRoleTitle;
        this.id = id;
        this.bandName = bandName;
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

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
}
