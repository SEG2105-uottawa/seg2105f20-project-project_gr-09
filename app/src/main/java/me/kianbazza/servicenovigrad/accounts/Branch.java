package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class Branch implements Parcelable {

    private boolean isDoneSetup;

    private String branchID;

    private String address;
    private String phoneNumber;

    private String openingTime;
    private String closingTime;

    // Empty Constructor
    public Branch() {

    }

    public Branch(boolean isDoneSetup, String branchID, String address, String phoneNumber, String openingTime, String closingTime) {
        this.isDoneSetup = isDoneSetup;
        this.branchID = branchID;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isDoneSetup() {
        return isDoneSetup;
    }

    public void setDoneSetup(boolean doneSetup) {
        isDoneSetup = doneSetup;
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

    protected Branch(Parcel in) {
        isDoneSetup = in.readByte() != 0;
        branchID = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        openingTime = in.readString();
        closingTime = in.readString();
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
        dest.writeByte((byte) (isDoneSetup ? 1 : 0));
        dest.writeString(branchID);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(openingTime);
        dest.writeString(closingTime);
    }
}
