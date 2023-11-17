package com.kainos.ea.cli;

import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.JobRole;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRoleTest {

    @Test
    void getterAndSetterJobRoleTest() {
        JobRole jobRole = new JobRole(1, "Software Engineer", "Band 5", "Software Development");

        int jobRoleId = 2;
        String jobRoleTitle = "Senior Software Engineer";
        String bandName = "Band 6";
        String capabilityName = "Advanced Software Development";

        jobRole.setJobRoleId(jobRoleId);
        jobRole.setJobRoleTitle(jobRoleTitle);
        jobRole.setBandName(bandName);
        jobRole.setCapabilityName(capabilityName);

        assertEquals(jobRole.getJobRoleId(), jobRoleId);
        assertEquals(jobRole.getJobRoleTitle(), jobRoleTitle);
        assertEquals(jobRole.getBandName(), bandName);
        assertEquals(jobRole.getCapabilityName(), capabilityName);
    }
}
