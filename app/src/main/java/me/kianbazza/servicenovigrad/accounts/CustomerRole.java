package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerRole extends UserRole implements Parcelable {

    public CustomerRole() {
        super(RoleName.CUSTOMER);
    }



    // PARCELABLE REQUIRED METHODS

    protected CustomerRole(Parcel in) {
        super(RoleName.fromString(in.readString()));
    }

    public static final Creator<CustomerRole> CREATOR = new Creator<CustomerRole>() {
        @Override
        public CustomerRole createFromParcel(Parcel in) {
            return new CustomerRole(in);
        }

        @Override
        public CustomerRole[] newArray(int size) {
            return new CustomerRole[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getRoleName().toString());
    }
}
