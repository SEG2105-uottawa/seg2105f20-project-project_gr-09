package me.kianbazza.servicenovigrad.accounts;

public class EmployeeAccount extends Account {

    public EmployeeAccount(String username, String email, String password) {

        super(username, email, password, Roles.Role.EMPLOYEE);
    }

}
