package me.kianbazza.servicenovigrad.misc;

import com.google.firebase.database.*;
import me.kianbazza.servicenovigrad.accounts.*;
import me.kianbazza.servicenovigrad.database.DatabaseManager;
import me.kianbazza.servicenovigrad.database.FirebaseCallback;

import java.util.HashMap;

public class AccountHelper {

    public boolean register(Account account) {

        if (isRegistered(account)) {
            return false;
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRoleRef = db.getReference("users/" + account.getUsername() + "/role");
        DatabaseReference userEmailRef = db.getReference("users/" + account.getUsername() + "/email");
        DatabaseReference userPassRef = db.getReference("users/" + account.getUsername() + "/password");

        userRoleRef.setValue(account.getRole());
        userEmailRef.setValue(account.getEmail());
        userPassRef.setValue(account.getPassword());

        return true;

    }

    /**
     * Checks if a username is already registered as an account
     * @param username
     * @return if username is registered
     */
    public boolean isRegistered(String username) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = db.getReference("users/");

        if (usersRef.child(username)==null) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isRegistered(Account account) {
        String username = account.getUsername();
        return isRegistered(username);

    }

    public static Role roleFromString(String s) {

        for (Role r : Role.values()) {

            if (s.equalsIgnoreCase( r.name() )) {
                return r;
            }

        }

        return Role.NONE;

    }

}
