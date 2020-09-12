package com.ghci20.codeathon.cowish;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class WishesInfo {

    public int aadhaarNumber;
    public ArrayList<String> wishes;

    public WishesInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public WishesInfo(int aadhaarNumber, ArrayList<String> wishes) {
        this.aadhaarNumber = aadhaarNumber;
        this.wishes = wishes;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("aadhaarNumber", aadhaarNumber);
        result.put("wishes", wishes);

        return result;
    }

}
