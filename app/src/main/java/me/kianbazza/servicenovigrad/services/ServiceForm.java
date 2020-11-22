package me.kianbazza.servicenovigrad.services;

import me.kianbazza.servicenovigrad.misc.Helper;

import java.util.HashMap;

public class ServiceForm {
    private HashMap<String, String> form;

    public ServiceForm() {

    }

    public ServiceForm(String requiredInfoStr) {
        form = new HashMap<>();
        String[] requiredInfo = convertRequiredInfoForSnapshot(requiredInfoStr);
        // Add the name of each piece of required info into the form as a key
        // Leave value as "" to be filled out by user when applying
        for(String infoName : requiredInfo) {
            form.put(infoName, "");
        }
    }

    public HashMap<String, String> getForm() {
        return form;
    }

    public String[] convertRequiredInfoForSnapshot(String requiredInfoStr) {

        String[] infoNames = requiredInfoStr.split(";");
        infoNames = Helper.trimArray(infoNames);

        for (int i=0; i < infoNames.length; i++) {
            infoNames[i] = Helper.threeDigitInt(i) + " " + infoNames[i];
        }

        return infoNames;
    }


}
