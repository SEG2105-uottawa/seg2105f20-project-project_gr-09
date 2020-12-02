package me.kianbazza.servicenovigrad.misc;

public class Vars {

    public static final String emailAddressRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String simpleStreetAddressRegex = "^\\d+\\s[A-z]+\\s[A-z]+";
    public static final String strippedPhoneNumberRegex = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$\n";

}
