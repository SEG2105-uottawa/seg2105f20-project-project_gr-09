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

    public static String threeDigitInt(int num) {
        if (num < 10 && num >= 0) {
            return "00" + num;
        } else if (num < 100) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

}
