package me.kianbazza.servicenovigrad.accounts;

public abstract class Account {

    private String username;
    private String email;
    private String password;
    private Roles.Role role;

    public Account(String username, String email, String password, Roles.Role role) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account convertAccountType(Roles.Role newRole) {
        switch (newRole) {
            case ADMIN:
                return new AdminAccount(username, email, password);
            case EMPLOYEE:
                return new EmployeeAccount(username, email, password);
            case CUSTOMER:
                return new CustomerAccount(username, email, password);
            default:
                return this;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Roles.Role getRole() {
        return role;
    }



}
