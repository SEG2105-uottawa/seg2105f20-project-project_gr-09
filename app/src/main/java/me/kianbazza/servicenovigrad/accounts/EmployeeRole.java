package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeRole extends UserRole implements Parcelable {

    public EmployeeRole() {
        super(RoleName.EMPLOYEE);
    }



    // PARCELABLE REQUIRED METHODS

    protected EmployeeRole(Parcel in) {
        super(RoleName.fromString(in.readString()));
    }

    public static final Creator<EmployeeRole> CREATOR = new Creator<EmployeeRole>() {
        @Override
        public EmployeeRole createFromParcel(Parcel in) {
            return new EmployeeRole(in);
        }

        @Override
        public EmployeeRole[] newArray(int size) {
            return new EmployeeRole[size];
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
