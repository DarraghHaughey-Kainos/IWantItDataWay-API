package org.kainos.ea.cli;

public class Capability {

    private int capability_id;
    private String capability_name;

    public Capability(int capability_id, String capability_name) {
        this.capability_id = capability_id;
        this.capability_name = capability_name;
    }

    public int getCapability_id() {
        return capability_id;
    }

    public void setCapability_id(int capability_id) {
        this.capability_id = capability_id;
    }

    public String getCapability_name() {
        return capability_name;
    }

    public void setCapability_name(String capability_name) {
        this.capability_name = capability_name;
    }
}
