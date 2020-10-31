package me.kianbazza.servicenovigrad.database;

import android.content.Context;
import me.kianbazza.servicenovigrad.accounts.Account;

import java.util.HashMap;

public interface FirebaseCallback {

    void getData(HashMap<String, String> data);

}
