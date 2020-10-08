package me.kianbazza.servicenovigrad.accounts;

public class EmployeeAccount extends Account {

    public EmployeeAccount(String name, String email, String password) {

        super(name, email, password, Roles.Role.EMPLOYEE);
    }

}
