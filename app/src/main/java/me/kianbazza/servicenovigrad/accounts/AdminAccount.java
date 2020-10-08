package me.kianbazza.servicenovigrad.accounts;

public class AdminAccount extends Account {

    public AdminAccount(String name, String email, String password) {

        super(name, email, password, Roles.Role.ADMIN);
    }

}
