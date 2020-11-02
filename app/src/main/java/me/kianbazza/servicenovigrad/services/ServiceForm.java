package me.kianbazza.servicenovigrad.services;

import java.util.Date;
import java.util.HashMap;

public class ServiceForm {
    private Date dateOfCompletion;
    private HashMap<String, String> inputFields;

    /**
     * Constructor to be used when defining the form template for a Service.
     *
     * @param requiredInfo the information to be filled out in the form
     *                     (just the name key, NOT the actual value)
     */
    public ServiceForm(String[] requiredInfo) {
        inputFields = new HashMap<>();
        // Add the name of each piece of required info into the form as a key
        // Leave value as "" to be filled out by user when applying
        for(String infoName : requiredInfo) {
            inputFields.put(infoName, "");
        }
    }

    public HashMap<String, String> getInputFields() {
        return inputFields;
    }
}
