package com.ghci20.codeathon.cowish.userlogin;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class FirebaseCallback implements ValueEventListener {
    private static boolean enteredPasswordCorrect;
    int enteredAadhaarNumber;
    String enteredPassword;
    private final static String TAG = "FirebaseCallback";
    private CountDownLatch latch;

    public FirebaseCallback(int enteredAadhaarNumber, String enteredPassword) {
        this.enteredAadhaarNumber = enteredAadhaarNumber;
        this.enteredPassword = enteredPassword;
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
            int aadhaarNumberFromDb = Integer.parseInt(postSnapshot.child("aadhaarNumber").getValue().toString());
            Log.i(TAG, "aadhaarNumberFromDb = " + aadhaarNumberFromDb);
            if(aadhaarNumberFromDb == enteredAadhaarNumber) {
                String passwordFromDb = postSnapshot.child("password").getValue().toString();
                Log.i(TAG, "passwordFromDb = " + passwordFromDb);
                if (enteredPassword.equals(passwordFromDb)) {
                    Log.i(TAG, "Fetched password. it's a match");
                    enteredPasswordCorrect = true;
                } else {
                    Log.i(TAG, "Fetched password. it's not a match");
                    enteredPasswordCorrect = false;
                }
                return;
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.e(TAG, "Encountered an error ", error.toException());
    }

}
