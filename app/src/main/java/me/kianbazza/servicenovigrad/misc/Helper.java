package me.kianbazza.servicenovigrad.misc;

import me.kianbazza.servicenovigrad.services.Service;

import java.util.ArrayList;
import java.util.Arrays;

public class Helper {

    public static String[] trimArray(String[] arr) {
        String[] temp = Arrays.copyOf(arr, arr.length);

        for (int i=0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }

        return temp;

    }

    @Deprecated
    public static String threeDigitInt(int num) {
        if (num < 10 && num >= 0) {
            return "00" + num;
        } else if (num < 100) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    public static boolean contains(ArrayList<Service> serviceArrayList, Service service) {

        for (Service s : serviceArrayList) {
            if ( s.getServiceID().equals(service.getServiceID()) ) {
                return true;
            }
        }

        return false;

    }



}
