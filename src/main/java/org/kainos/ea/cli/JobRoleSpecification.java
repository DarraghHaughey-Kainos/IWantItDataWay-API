package org.kainos.ea.cli;

import java.util.List;

public class JobRoleSpecification {

    private int jobRoleId;
    private String jobRoleTitle;
    private String jobRoleSharepointLink;
    private List<String> specificationText;

    public JobRoleSpecification(int jobRoleId, String jobRoleTitle, String jobRoleSharepointLink, List<String> specificationText) {
        this.jobRoleId = jobRoleId;
        this.jobRoleTitle = jobRoleTitle;
        this.jobRoleSharepointLink = jobRoleSharepointLink;
        this.specificationText = specificationText;
    }

    public int getJobRoleId() { return jobRoleId; }

    public void setJobRoleId(int jobRoleId) { this.jobRoleId = jobRoleId; }

    public String getJobRoleTitle() { return jobRoleTitle; }

    public void setJobRoleTitle(String jobRoleTitle) { this.jobRoleTitle = jobRoleTitle; }

    public String getJobRoleSharepointLink() { return jobRoleSharepointLink; }

    public void setJobRoleSharepointLink(String jobRoleSharepointLink) { this.jobRoleSharepointLink = jobRoleSharepointLink; }

    public List<String> getSpecificationText() { return specificationText; }

    public void setSpecificationText(List<String> specificationText) { this.specificationText = specificationText; }
}
