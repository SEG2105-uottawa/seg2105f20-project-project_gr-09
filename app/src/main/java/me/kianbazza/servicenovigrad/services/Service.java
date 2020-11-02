package me.kianbazza.servicenovigrad.services;

public class Service {

    private String name;
    private String displayName;
    private double price;
    private ServiceForm requiredCustomerInfo;
    private ServiceDocument[] requiredDocuments;

    public Service(String name, String displayName, double price, ServiceForm requiredCustomerInfo, ServiceDocument[] requiredDocuments) {
        this.name = name;
        this.displayName = displayName;
        this.price = price;
        this.requiredCustomerInfo = requiredCustomerInfo;
        this.requiredDocuments = requiredDocuments;
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

    public ServiceForm getRequiredCustomerInfo() {
        return requiredCustomerInfo;
    }

    public void setRequiredCustomerInfo(ServiceForm requiredCustomerInfo) {
        this.requiredCustomerInfo = requiredCustomerInfo;
    }

    public ServiceDocument[] getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(ServiceDocument[] requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }




}
