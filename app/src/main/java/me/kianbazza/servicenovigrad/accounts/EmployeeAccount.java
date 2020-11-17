package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeAccount extends Account {

    public EmployeeAccount(String username, String email, String password) {

        super(username, email, password, Role.EMPLOYEE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getUsername());
        dest.writeString(getEmail());
        dest.writeString(getPassword());
        dest.writeString(getRole().name());
    }

    public static final Parcelable.Creator<EmployeeAccount> CREATOR = new Parcelable.Creator<EmployeeAccount>() {
        public EmployeeAccount createFromParcel(Parcel in) {
            return new EmployeeAccount(in);
        }

        public EmployeeAccount[] newArray(int size) {
            return new EmployeeAccount[size];
        }
    };

    private EmployeeAccount(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), Role.valueOf(in.readString()));
    }
}
