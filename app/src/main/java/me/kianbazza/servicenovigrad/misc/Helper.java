package me.kianbazza.servicenovigrad.misc;

import java.util.Arrays;

public class Helper {

    public static String[] trimArray(String[] arr) {
        String[] temp = Arrays.copyOf(arr, arr.length);

        for (int i=0; i < temp.length; i++) {
            temp[i] = temp[i].trim();
        }

        return temp;

    }

}
