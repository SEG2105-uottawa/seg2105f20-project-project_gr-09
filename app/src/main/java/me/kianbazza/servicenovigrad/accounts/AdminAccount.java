package me.kianbazza.servicenovigrad.accounts;

public class AdminAccount extends Account {

    public AdminAccount(String username, String email, String password) {

        super(username, email, password, Roles.Role.ADMIN);
    }

}
