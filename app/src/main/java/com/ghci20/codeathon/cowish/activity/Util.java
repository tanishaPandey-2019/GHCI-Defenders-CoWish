package com.ghci20.codeathon.cowish.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ghci20.codeathon.cowish.userlogin.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.ghci20.codeathon.cowish.constants.FIREBASE_USERS;
import static com.ghci20.codeathon.cowish.constants.SHARED_PREF_AADHAAR_NUMBER;

public class Util {

    public static String getUserFirebaseKey(Context context) {
        final int sharedPrefAadhaarNumber = context.getSharedPreferences("default", Context.MODE_PRIVATE)
                .getInt(SHARED_PREF_AADHAAR_NUMBER, 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference(FIREBASE_USERS);
        String childkey = userRef.child("aadhaarNumber").getKey();
        String userRefkey = userRef.getKey();
        return userRef.getKey();
    }

    public static String addWishAgainstParticularAadhaar(int aadhaarNumber) {
        return "";
    }
}
