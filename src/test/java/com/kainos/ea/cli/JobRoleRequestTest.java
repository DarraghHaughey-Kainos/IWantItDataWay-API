package com.kainos.ea.cli;

import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.JobRole;
import org.kainos.ea.cli.JobRoleRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRoleRequestTest {

    @Test
    void getterAndSetterJobRoleRequestTest() {
        JobRoleRequest jobRoleRequest = new JobRoleRequest("Software Engineer", 5, 10, "http://example.com");

        String jobRoleTitle = "Senior Software Engineer";
        int bandId = 6;
        int capabilityId = 12;
        String sharepointLink = "http://newexample.com";

        jobRoleRequest.setJobRoleTitle(jobRoleTitle);
        jobRoleRequest.setBandId(bandId);
        jobRoleRequest.setCapabilityId(capabilityId);
        jobRoleRequest.setSharepointLink(sharepointLink);

        assertEquals(jobRoleRequest.getJobRoleTitle(), jobRoleTitle);
        assertEquals(jobRoleRequest.getBandId(), bandId);
        assertEquals(jobRoleRequest.getCapabilityId(), capabilityId);
        assertEquals(jobRoleRequest.getSharepointLink(), sharepointLink);
    }
}
