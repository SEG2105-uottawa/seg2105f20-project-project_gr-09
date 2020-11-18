package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class AdminRole extends UserRole implements Parcelable {

    public AdminRole() {
        super(RoleName.ADMIN);
    }



    // PARCELABLE REQUIRED METHODS

    protected AdminRole(Parcel in) {
        super(RoleName.fromString(in.readString()));
    }

    public static final Creator<AdminRole> CREATOR = new Creator<AdminRole>() {
        @Override
        public AdminRole createFromParcel(Parcel in) {
            return new AdminRole(in);
        }

        @Override
        public AdminRole[] newArray(int size) {
            return new AdminRole[size];
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
