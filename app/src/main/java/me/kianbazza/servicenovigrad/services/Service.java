package me.kianbazza.servicenovigrad.services;

public class Service {

    private String name;
    private String displayName;
    private double price;
    private ServiceForm formTemplate;
    private ServiceDocument[] documentsTemplate;

    public Service(String name, String displayName, double price, ServiceForm formTemplate, ServiceDocument[] documentsTemplate) {
        this.name = name;
        this.displayName = displayName;
        this.price = price;
        this.formTemplate = formTemplate;
        this.documentsTemplate = documentsTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ServiceForm getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(ServiceForm formTemplate) {
        this.formTemplate = formTemplate;
    }

    public ServiceDocument[] getDocumentsTemplate() {
        return documentsTemplate;
    }

    public void setDocumentsTemplate(ServiceDocument[] documentsTemplate) {
        this.documentsTemplate = documentsTemplate;
    }




}
