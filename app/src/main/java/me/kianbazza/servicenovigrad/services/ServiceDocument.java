package me.kianbazza.servicenovigrad.services;


import java.net.URL;
import java.util.Date;

public class ServiceDocument {

    private String name;
    private URL linkToDocument;
    private Date uploadDate;

    public ServiceDocument(String name, URL linkToDocument) {
        this.name = name;
        this.linkToDocument = linkToDocument;
    }

    public String getName() {
        return name;
    }

    public URL getLinkToDocument() {
        return linkToDocument;
    }
}
