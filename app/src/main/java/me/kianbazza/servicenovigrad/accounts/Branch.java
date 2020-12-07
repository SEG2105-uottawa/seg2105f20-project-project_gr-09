package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;
import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;
import java.util.Arrays;

public class Branch implements Parcelable {

    private boolean doneSetup;

    private String branchID;

    private String address;
    private String phoneNumber;

    private String openingTime;
    private String closingTime;

    private ArrayList<Service> services;

    // Empty Constructor
    public Branch() {

    }

    public Branch(boolean doneSetup, String branchID, String address, String phoneNumber, String openingTime, String closingTime) {
        this.doneSetup = doneSetup;
        this.branchID = branchID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean getDoneSetup() {
        return doneSetup;
    }

    public void setDoneSetup(boolean doneSetup) {
        this.doneSetup = doneSetup;
    }

    public String getBranchID() {return branchID;}

    public void setBranchID(String branchID) {this.branchID = branchID;}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

//    public ArrayList<Service> getServices() {
//        return services;
//    }
//
//    public void setServices(ArrayList<Service> services) {
//        this.services = services;
//    }

    public String generateWorkingHours() {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append(" Monday to Friday: ").append(getOpeningTime()).append(" - ").append(getClosingTime());
        strBuilder.append("\nSaturday: CLOSED");
        strBuilder.append("\nSunday: CLOSED");

//        strBuilder.append(" Mon: ").append(getOpeningTime()).append(" - ").append(getClosingTime());
//        strBuilder.append("\nTues: ").append(getOpeningTime()).append(" - ").append(getClosingTime());
//        strBuilder.append("\nWed: ").append(getOpeningTime()).append(" - ").append(getClosingTime());
//        strBuilder.append("\nThurs: ").append(getOpeningTime()).append(" - ").append(getClosingTime());
//        strBuilder.append("\nFri: ").append(getOpeningTime()).append(" - ").append(getClosingTime());
//        strBuilder.append("\nSat: ").append("CLOSED");
//        strBuilder.append("\nSun: ").append("CLOSED");

        return strBuilder.toString().trim();
    }

    protected Branch(Parcel in) {
        doneSetup = in.readByte() != 0;
        branchID = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        openingTime = in.readString();
        closingTime = in.readString();
        // services = new ArrayList<>(Arrays.asList((Service[]) in.readParcelableArray(Service.class.getClassLoader())));
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (doneSetup ? 1 : 0));
        dest.writeString(branchID);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(openingTime);
        dest.writeString(closingTime);
        // dest.writeParcelableArray(services.toArray(new Service[0]), flags);
    }
}
