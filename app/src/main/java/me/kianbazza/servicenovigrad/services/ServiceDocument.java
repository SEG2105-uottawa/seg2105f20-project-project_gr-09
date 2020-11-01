package me.kianbazza.servicenovigrad.services;


import java.net.URL;

public class ServiceDocument {

    private String name;
    private URL linkToDoc;

    public ServiceDocument(String name, URL linkToDoc) {
        this.name = name;
        this.linkToDoc = linkToDoc;
    }

    public String getName() {
        return name;
    }

    public URL getLinkToDoc() {
        return linkToDoc;
    }
}
