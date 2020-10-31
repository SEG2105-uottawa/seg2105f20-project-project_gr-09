package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcel;
import android.os.Parcelable;
import me.kianbazza.servicenovigrad.misc.AccountHelper;

public class AdminAccount extends Account {
    // CONSTRUCTOR
    public AdminAccount(String username, String email, String password) {

        super(username, email, password, Role.ADMIN);
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

    public static final Parcelable.Creator<AdminAccount> CREATOR = new Parcelable.Creator<AdminAccount>() {
        public AdminAccount createFromParcel(Parcel in) {
            return new AdminAccount(in);
        }

        public AdminAccount[] newArray(int size) {
            return new AdminAccount[size];
        }
    };

    private AdminAccount(Parcel in) {
        super(in.readString(), in.readString(), in.readString(), AccountHelper.roleFromString(in.readString()));
    }
}
