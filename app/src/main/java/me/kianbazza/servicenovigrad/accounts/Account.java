package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    private String username;
    private String email;
    private String password;
    private Role role;

    private String branchID;

    // Empty constructor
    public Account() {

    }

    public Account(String username, String email, String password, Role role) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setBranch(String branchID) {
        if (role==Role.EMPLOYEE) {
            this.branchID = branchID;
        }
    }

    public String getBranchID() {
        if (role==Role.EMPLOYEE) {
            return branchID;
        } else {
            return null;
        }

    }


    /**
     * PARCELABLE REQUIRED METHODS
     */

    protected Account(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        role = Role.fromString(in.readString());
        branchID = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(role.toString());
        dest.writeString(branchID);
    }
}
