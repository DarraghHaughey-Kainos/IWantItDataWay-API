package org.kainos.ea.cli;


public class Specification {

    private int specificationId;
    private String specificationText;

    public Specification(int specificationId, String specificationText) {
        this.specificationId = specificationId;
        this.specificationText = specificationText;
    }


    public int getSpecificationId() { return specificationId; }

    public void setSpecificationId(int specificationId) { this.specificationId = specificationId; }

    public String getSpecificationText() { return specificationText; }

    public void setSpecificationText(String specificationText) { this.specificationText = specificationText; }
}
