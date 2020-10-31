package me.kianbazza.servicenovigrad.database;

import android.content.Context;
import com.google.firebase.database.*;

import java.util.HashMap;

public class DatabaseManager {

    public void readChildrenData(String path, FirebaseCallback firebaseCallback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(path);

        HashMap<String, String> data = new HashMap<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    data.put(child.getKey(), child.getValue(String.class));
                }

                firebaseCallback.getData(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
