package me.kianbazza.servicenovigrad.services;

import android.os.Parcel;
import android.os.Parcelable;
import me.kianbazza.servicenovigrad.accounts.Account;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceRequest implements Parcelable {

    // Instance Variables
    private String requestID;
    private ServiceRequestStatus requestStatus;
    private Account customer;

    private ArrayList<ServiceFormEntry<String, String>> customerInfo;
    private ArrayList<ServiceDocument> requiredDocuments;

    private Service service;

    // Empty Constructor
    public ServiceRequest() {

    }

    // Main Constructor
    public ServiceRequest(String requestID, ServiceRequestStatus requestStatus, Account customer, ArrayList<ServiceFormEntry<String, String>> customerInfo, ArrayList<ServiceDocument> requiredDocuments, Service service) {
        this.requestID = requestID;
        this.requestStatus = requestStatus;
        this.customer = customer;
        this.customerInfo = customerInfo;
        this.requiredDocuments = requiredDocuments;
        this.service = service;
    }

    /**** Parcelable Methods ****/

    protected ServiceRequest(Parcel in) {
        requestID = in.readString();
        requestStatus = ServiceRequestStatus.fromString(in.readString());
        customer = in.readParcelable(Account.class.getClassLoader());
        customerInfo = new ArrayList<>(Arrays.asList((ServiceFormEntry<String, String>[]) in.readParcelableArray(ServiceFormEntry.class.getClassLoader())));
        requiredDocuments = new ArrayList<>(Arrays.asList((ServiceDocument[]) in.readParcelableArray(ServiceDocument.class.getClassLoader())));
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
        dest.writeString(requestStatus.name());
        dest.writeParcelable(customer, flags);
        dest.writeParcelableArray(customerInfo.toArray(new ServiceFormEntry[0]), flags);
        dest.writeParcelableArray(requiredDocuments.toArray(new ServiceDocument[0]), flags);
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

    public ServiceRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(ServiceRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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
