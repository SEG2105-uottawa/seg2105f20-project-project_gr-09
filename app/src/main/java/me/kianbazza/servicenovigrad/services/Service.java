package me.kianbazza.servicenovigrad.services;

public class Service {

    private String name;
    private double price;
    private ServiceForm formTemplate;
    private ServiceDocument[] documentsTemplate;

    public Service(String name, double price, ServiceForm formTemplate, ServiceDocument[] documentsTemplate) {
        this.name = name;
        this.price = price;
        this.formTemplate = formTemplate;
        this.documentsTemplate = documentsTemplate;
    }




}
