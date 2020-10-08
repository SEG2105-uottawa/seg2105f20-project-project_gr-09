package me.kianbazza.servicenovigrad.accounts;

public class CustomerAccount extends Account {

    public CustomerAccount(String name, String email, String password) {

        super(name, email, password, Roles.Role.CUSTOMER);
    }

}
