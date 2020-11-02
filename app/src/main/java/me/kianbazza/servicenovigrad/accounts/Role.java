package me.kianbazza.servicenovigrad.accounts;

import com.google.firebase.database.annotations.NotNull;

public enum Role {

    ADMIN,
    EMPLOYEE,
    CUSTOMER,
    NONE;

    public static Role fromString(@NotNull String s) {
        s.trim();
        if (s.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        } else if (s.equalsIgnoreCase("EMPLOYEE")) {
            return EMPLOYEE;
        } else if (s.equalsIgnoreCase("CUSTOMER")) {
            return CUSTOMER;
        } else {
            return NONE;
        }
    }

}
