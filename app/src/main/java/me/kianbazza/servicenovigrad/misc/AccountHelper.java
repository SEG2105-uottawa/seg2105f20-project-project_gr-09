package me.kianbazza.servicenovigrad.misc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.accounts.*;

public class AccountHelper {

    public void registerAccount(Account account) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRoleRef = db.getReference("users/" + account.getUsername() + "/role");
        DatabaseReference userEmailRef = db.getReference("users/" + account.getUsername() + "/email");
        DatabaseReference userPassRef = db.getReference("users/" + account.getUsername() + "/password");

        userRoleRef.setValue(account.getRole());
        userEmailRef.setValue(account.getEmail());
        userPassRef.setValue(account.getPassword());

    }

}
