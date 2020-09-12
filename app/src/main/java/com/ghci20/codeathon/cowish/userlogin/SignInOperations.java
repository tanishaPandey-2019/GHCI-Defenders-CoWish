package com.ghci20.codeathon.cowish.userlogin;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.ghci20.codeathon.cowish.constants.FIREBASE_USERS;


public class SignInOperations {
    private static boolean enteredPasswordCorrect;

    private static final String TAG = "UserOperations";

    public static boolean isPasswordCorrect(final int enteredAadhaarNumber, final String enteredPassword) {
        Log.i(TAG, "enteredAadhaarNumber = " + enteredAadhaarNumber + " enteredPassword = " + enteredPassword);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef =  database.getReference(FIREBASE_USERS);
        final CountDownLatch latch = new CountDownLatch(1);
//        final SettableApiFuture<DataSnapshot> future = SettableApiFuture.create();
        userRef.getRef().addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
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
                        break;
                    }
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Encountered an error ", error.toException());
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            Thread.sleep(10000); // 10 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return enteredPasswordCorrect;
    }
}
