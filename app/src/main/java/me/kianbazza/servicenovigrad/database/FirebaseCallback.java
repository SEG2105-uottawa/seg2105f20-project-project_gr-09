package me.kianbazza.servicenovigrad.database;

import com.google.firebase.database.DataSnapshot;
;
import java.util.HashMap;

public interface FirebaseCallback {

    void getData(HashMap<String, Object> data);

    void getRef(HashMap<String, DataSnapshot> data);

}
