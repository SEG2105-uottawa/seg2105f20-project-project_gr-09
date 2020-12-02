package me.kianbazza.servicenovigrad.services;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Service implements Parcelable {

    // Instance Variables
    private String serviceID;

    private String name;
    private double price;

    private ArrayList<String> requiredInfo;
    private ArrayList<String> requiredDocuments;

    // Empty Constructor
    public Service() {

    }

    // Main Constructor
    public Service(String serviceID, String name, double price, ArrayList<String> requiredInfo, ArrayList<String> requiredDocuments) {
        this.serviceID = serviceID;
        this.name = name;
        this.price = price;
        this.requiredInfo = requiredInfo;
        this.requiredDocuments = requiredDocuments;
    }

    /**** Parcelable Methods ****/

    protected Service(Parcel in) {
        serviceID = in.readString();
        name = in.readString();
        price = in.readDouble();
        requiredInfo = in.createStringArrayList();
        requiredDocuments = in.createStringArrayList();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceID);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeStringList(requiredInfo);
        dest.writeStringList(requiredDocuments);
    }

    /**** End of Parcelable Methods ****/

    /**** Getters and Setters ****/

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
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

    public ArrayList<String> getRequiredInfo() {
        return requiredInfo;
    }

    public void setRequiredInfo(ArrayList<String> requiredInfo) {
        this.requiredInfo = requiredInfo;
    }

    public ArrayList<String> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(ArrayList<String> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    /**** End of Getters and Setters ****/

    public String generateCustomerInfoWithSeparator() {

        StringBuilder sb = new StringBuilder();

        Iterator<String> itr = requiredInfo.iterator();

        while (itr.hasNext()) {
            String customerInfo = itr.next();
            sb.append(customerInfo);

            if (itr.hasNext()) {
                sb.append("; ");
            }
        }

        return sb.toString().trim();

    }

    public String generateDocumentNamesWithSeparator() {

        StringBuilder sb = new StringBuilder();

        Iterator<String> itr = requiredDocuments.iterator();

        while (itr.hasNext()) {
            String docName = itr.next();
            sb.append(docName);

            if (itr.hasNext()) {
                sb.append("; ");
            }
        }

        return sb.toString().trim();

    }
}
