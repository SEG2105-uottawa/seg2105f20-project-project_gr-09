package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;
import me.kianbazza.servicenovigrad.misc.AccountHelper;

public class CustomerAccount extends Account {

    public CustomerAccount(String username, String email, String password) {

        super(username, email, password, Role.CUSTOMER);
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

    public static final Parcelable.Creator<CustomerAccount> CREATOR = new Parcelable.Creator<CustomerAccount>() {
        public CustomerAccount createFromParcel(Parcel in) {
            return new CustomerAccount(in);
        }

        public CustomerAccount[] newArray(int size) {
            return new CustomerAccount[size];
        }
    };

    private CustomerAccount(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), Role.valueOf(in.readString()));
    }
}
