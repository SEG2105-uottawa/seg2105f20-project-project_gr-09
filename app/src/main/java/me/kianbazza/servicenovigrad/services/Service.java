package me.kianbazza.servicenovigrad.services;

import java.util.ArrayList;

public class Service {

    private String id;
    private String name;
    private double price;
    private ArrayList<ServiceFormEntry> form;
    private ArrayList<ServiceDocument> requiredDocuments;

    public Service() {

    }

    public Service(String id, String name, double price, ArrayList<ServiceFormEntry> form, ArrayList<ServiceDocument> requiredDocuments) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.form = form;
        this.requiredDocuments = requiredDocuments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<ServiceFormEntry> getForm() {
        return form;
    }

    public void setForm(ArrayList<ServiceFormEntry> form) {
        this.form = form;
    }

    public ArrayList<ServiceDocument> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(ArrayList<ServiceDocument> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }
}
