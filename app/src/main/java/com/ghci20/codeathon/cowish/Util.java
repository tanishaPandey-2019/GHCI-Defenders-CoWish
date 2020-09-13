package com.ghci20.codeathon.cowish;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.ghci20.codeathon.cowish.constants.FIREBASE_WISHES;
import static com.ghci20.codeathon.cowish.constants.SHARED_PREF_AADHAAR_NUMBER;
import static com.ghci20.codeathon.cowish.constants.SHARED_PREF_ALL_WISHLIST;
import static com.ghci20.codeathon.cowish.constants.SHARED_PREF_PASSWORD_MATCHED;
import static com.ghci20.codeathon.cowish.constants.SHARED_PREF_WISHLIST;

public class Util {

    private static final String TAG = "Util";

    public static void setAadhaarNumberToSharedPref(Context context, String aadhaarNumberEnteredByUser) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREF_AADHAAR_NUMBER, Integer.parseInt(aadhaarNumberEnteredByUser));
        editor.apply();
        Log.i(TAG, "Shared pref saved");
    }

    public static int getAadhaarNumberFromSharedPref(Context context) {
        return context.getSharedPreferences("default", MODE_PRIVATE)
                .getInt(SHARED_PREF_AADHAAR_NUMBER, 0);
    }

    private static void setUserWishListToSharedPref(Context context, ArrayList<String> wishlist) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> wishSet = new HashSet<String>(wishlist);
        editor.putStringSet(SHARED_PREF_WISHLIST, wishSet);
        editor.apply();
        Log.i(TAG, "Shared pref saved");
    }

    public static ArrayList<String> getUserWishListFromSharedPref(Context context) {
        Set<String> wishSet = context.getSharedPreferences("default", MODE_PRIVATE)
                .getStringSet(SHARED_PREF_WISHLIST, null);
        if (wishSet == null) {
            return null;
        }
        return new ArrayList<String>(wishSet);
    }

    private static void setAllWishListToSharedPref(Context context, ArrayList<String> wishlist) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> wishSet = new HashSet<String>(wishlist);
        editor.putStringSet(SHARED_PREF_ALL_WISHLIST, wishSet);
        editor.apply();
    }

    public static ArrayList<String> getAllWishListFromSharedPref(Context context) {
        Set<String> wishSet = context.getSharedPreferences("default", MODE_PRIVATE)
                .getStringSet(SHARED_PREF_ALL_WISHLIST, null);
        if (wishSet == null) {
            return null;
        }
        return new ArrayList<String>(wishSet);
    }

    public static void setPasswordMatchedToSharedPref(Context context, Boolean isPasswordMatch) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("default", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHARED_PREF_PASSWORD_MATCHED, isPasswordMatch);
        editor.apply();
        Log.i(TAG, "Shared pref saved");
    }

    public static boolean getPasswordMatchedFromSharedPref(Context context) {
        return context.getSharedPreferences("default", MODE_PRIVATE)
                .getBoolean(SHARED_PREF_PASSWORD_MATCHED, false);
    }

    public static void getUserWishListUserFromFirebase(final Context context) {
        final int sharedPrefAadhaarNumber = Util.getAadhaarNumberFromSharedPref(context);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference wishesRef = database.getReference(FIREBASE_WISHES);
        Query query = wishesRef
                .orderByChild("aadhaarNumber")
                .equalTo(sharedPrefAadhaarNumber);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    setUserWishListToSharedPref(context, (ArrayList<String>) postSnapshot.child("wishes").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Encountered an error ", error.toException());
            }
        });
    }

    public static void getAllWishListFromFirebase(final Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference wishesRef = database.getReference(FIREBASE_WISHES);
        wishesRef.getRef().addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> allWishlistsInFirebase = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            allWishlistsInFirebase.add(postSnapshot.child("wishes").getValue().toString());
                        }
                        setAllWishListToSharedPref(context, allWishlistsInFirebase);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.e(TAG, "Encountered an error ", error.toException());
                    }
                });

    }


}
