package me.kianbazza.servicenovigrad.accounts;

public enum Role {
    CUSTOMER,
    EMPLOYEE,
    ADMIN;

    public static Role fromString(String s) {
        String roleStr = s.trim();

        if (roleStr.equalsIgnoreCase("CUSTOMER")) {
            return CUSTOMER;
        } else if (roleStr.equalsIgnoreCase("EMPLOYEE")) {
            return EMPLOYEE;
        } else if (roleStr.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        } else {
            return null;
        }

    }

}
