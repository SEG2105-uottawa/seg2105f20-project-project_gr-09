package me.kianbazza.servicenovigrad.services;

import java.util.AbstractMap;

public class ServiceFormEntry extends AbstractMap.SimpleEntry {

    // Empty constructor
    public ServiceFormEntry() {
        super("", new Object());


    }

    public ServiceFormEntry(String key, Object value) {
        super(key, value);
    }
}
