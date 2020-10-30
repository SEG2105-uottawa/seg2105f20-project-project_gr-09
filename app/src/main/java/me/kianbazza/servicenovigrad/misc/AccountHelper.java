package me.kianbazza.servicenovigrad.misc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.accounts.*;

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

    private boolean authenticate(String username, String password) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRef = db.getReference("users/" + username);
        DatabaseReference userPassRef = userRef.child("password");

        if (password.equals(userPassRef.toString())) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Returns an Account object corresponding to the account with the inputted username
     * and password. Username must correspond to an existing account, and password must
     * match that of the account with the inputted username.
     * @param username the account's username
     * @param password the account's password
     * @return Account object corresponding to the inputted username and password
     * @return Null if username does not match any account, or password is incorrect
     */
    public Account getAccount(String username, String password) {

        if (!isRegistered(username)) {
            return null;
        }

        if ( authenticate(username, password) ) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference userRef = db.getReference("users/" + username);

            DatabaseReference userRoleRef = userRef.child("role");
            DatabaseReference userEmailRef = userRef.child("email");
            DatabaseReference userPassRef = userRef.child("password");

            Account account;

            switch (Roles.fromString(userRoleRef.toString())) {
                case CUSTOMER:
                    account = new CustomerAccount(username, userEmailRef.toString(), password);
                    break;
                case EMPLOYEE:
                    account = new EmployeeAccount(username, userEmailRef.toString(), password);
                    break;
                case ADMIN:
                    account = new AdminAccount(username, userEmailRef.toString(), password);
                    break;
                default:
                    return null;
            }

            return account;

        } else {
            return null;
        }

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

}
