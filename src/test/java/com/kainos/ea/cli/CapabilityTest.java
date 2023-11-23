package com.kainos.ea.cli;

import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.Capability;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CapabilityTest {

    @Test
    void getterAndSetterBandTest() {
        Capability capability = new Capability(0,"Test");

        int capabilityId = 1;
        String capabilityName = "Capability";

        capability.setCapabilityId(capabilityId);
        capability.setCapabilityName(capabilityName);

        assertEquals(capability.getCapabilityId(), capabilityId);
        assertEquals(capability.getCapabilityName(), capabilityName);
    }
}
