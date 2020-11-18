package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    private String username;
    private String email;
    private String password;
    private UserRole role;

    public Account(String username, String email, String password, UserRole.RoleName roleName) {

        this.username = username;
        this.email = email;
        this.password = password;

        switch (roleName) {
            case CUSTOMER:
                this.role = new CustomerRole();
                break;
            case EMPLOYEE:
                this.role = new EmployeeRole();
                break;
            case ADMIN:
                this.role = new AdminRole();
                break;
            default:
                this.role = null;
                break;
        }

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

    public UserRole getRole() {
        return role;
    }

    /**
     * PARCELABLE REQUIRED METHODS
     */

    protected Account(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
        role = in.readParcelable(UserRole.class.getClassLoader());
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
        dest.writeParcelable(role, 0);
    }
}
