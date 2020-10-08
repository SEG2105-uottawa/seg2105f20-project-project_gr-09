package me.kianbazza.servicenovigrad.accounts;

public abstract class Account {

    private String name;
    private String email;
    private String password;
    private Roles.Role role;

    public Account(String name, String email, String password, Roles.Role role) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
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
