package me.kianbazza.servicenovigrad.services;

import android.os.Parcel;
import android.os.Parcelable;
import me.kianbazza.servicenovigrad.accounts.Account;

import java.util.ArrayList;

public class ServiceRequest implements Parcelable {

    // Instance Variables
    private String requestID;
    private Account customer;

    private ArrayList<ServiceFormEntry<String, String>> customerInfo;
    private ArrayList<ServiceDocument> requiredDocuments;

    private Service service;

    // Empty Constructor
    public ServiceRequest() {

    }

    // Main Constructor
    public ServiceRequest(String requestID, Account customer, ArrayList<ServiceFormEntry<String, String>> customerInfo, ArrayList<ServiceDocument> requiredDocuments, Service service) {
        this.requestID = requestID;
        this.customer = customer;
        this.customerInfo = customerInfo;
        this.requiredDocuments = requiredDocuments;
        this.service = service;
    }

    /**** Parcelable Methods ****/

    protected ServiceRequest(Parcel in) {
        requestID = in.readString();
        customer = in.readParcelable(Account.class.getClassLoader());
        service = in.readParcelable(Service.class.getClassLoader());
    }

    public static final Creator<ServiceRequest> CREATOR = new Creator<ServiceRequest>() {
        @Override
        public ServiceRequest createFromParcel(Parcel in) {
            return new ServiceRequest(in);
        }

        @Override
        public ServiceRequest[] newArray(int size) {
            return new ServiceRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestID);
        dest.writeParcelable(customer, flags);
        dest.writeParcelable(service, flags);
    }

    /**** End of Parcelable Methods ****/

    /**** Getters and Setters ****/

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public ArrayList<ServiceFormEntry<String, String>> getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(ArrayList<ServiceFormEntry<String, String>> customerInfo) {
        this.customerInfo = customerInfo;
    }

    public ArrayList<ServiceDocument> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(ArrayList<ServiceDocument> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    /**** End of Getters and Setters ****/


}
