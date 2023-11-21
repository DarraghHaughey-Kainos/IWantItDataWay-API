package org.kainos.ea.cli;

public class JobRole {
    private int jobRoleId;
    private String jobRoleTitle;
    private String capabilityName;
    private String sharepointLink;
    private String specificationText;
    private String bandName;

    public JobRole(int jobRoleId, String jobRoleTitle, String capabilityName, String sharepointLink, String specificationText, String bandName) {
        this.jobRoleId = jobRoleId;
        this.jobRoleTitle = jobRoleTitle;
        this.capabilityName = capabilityName;
        this.sharepointLink = sharepointLink;
        this.specificationText = specificationText;
        this.bandName = bandName;
    }

    public int getJobRoleId() { return jobRoleId; }

    public void setJobRoleId(int jobRoleId) { this.jobRoleId = jobRoleId; }

    public String getJobRoleTitle() { return jobRoleTitle; }

    public void setJobRoleTitle(String jobRoleTitle) { this.jobRoleTitle = jobRoleTitle; }

    public String getCapabilityName() { return capabilityName; }

    public void setCapabilityName(String capabilityName) { this.capabilityName = capabilityName; }

    public String getSharepointLink() { return sharepointLink; }

    public void setSharepointLink(String sharepointLink) { this.sharepointLink = sharepointLink; }

    public String getSpecificationText() { return specificationText; }

    public void setSpecificationText(String specificationText) { this.specificationText = specificationText; }

    public String getBandName() { return bandName; }

    public void setBandName(String bandName) { this.bandName = bandName; }
}
