package org.kainos.ea.cli;

public class Capability {

    private int capabilityId;
    private String capabilityName;

    public Capability(int capabilityId, String capabilityName) {
        this.capabilityId = capabilityId;
        this.capabilityName = capabilityName;
    }

    public int getCapabilityId() {
        return capabilityId;
    }

    public void setCapabilityId(int capabilityId) {
        this.capabilityId = capabilityId;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public void setCapabilityName(String capabilityName) {
        this.capabilityName = capabilityName;
    }
}
