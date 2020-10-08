package me.kianbazza.servicenovigrad.accounts;

public class Roles {

    enum Role {

        ADMIN,
        EMPLOYEE,
        CUSTOMER,
        NONE;

    }

    public static Role fromString(String s) {

        for (Role r : Role.values()) {

            if (s.equalsIgnoreCase( r.name() )) {
                return r;
            }

        }

        return Role.NONE;

    }

}


