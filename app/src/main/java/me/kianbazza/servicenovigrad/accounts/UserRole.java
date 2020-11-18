package me.kianbazza.servicenovigrad.accounts;

import android.os.Parcelable;

public abstract class UserRole implements Parcelable {

    public enum RoleName {
        CUSTOMER,
        EMPLOYEE,
        ADMIN;

        public static RoleName fromString(String s) {
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

    private RoleName roleName;

    public UserRole(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }
}
