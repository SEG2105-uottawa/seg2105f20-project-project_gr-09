package me.kianbazza.servicenovigrad.services;

public class ServiceDocument {

    private String name;
    private String linkToUpload;

    public ServiceDocument() {

    }

    public ServiceDocument(String name, String linkToDocument) {
        this.name = name;
        this.linkToUpload = linkToDocument;
    }

    public String getName() {
        return name;
    }

    public String getLinkToUpload() {
        return linkToUpload;
    }
}
