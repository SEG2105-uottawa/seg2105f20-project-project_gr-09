package me.kianbazza.servicenovigrad.accounts;

public class CustomerAccount extends Account {

    public CustomerAccount(String username, String email, String password) {

        super(username, email, password, Roles.Role.CUSTOMER);
    }

}
