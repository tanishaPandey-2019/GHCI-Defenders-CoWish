package com.ghci20.codeathon.cowish;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class WishesInfo {

    public int aadhaarNumber;
    public List<String> wishes;

    public WishesInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public WishesInfo(int aadhaarNumber,
                      List<String> wishes) {
        this.aadhaarNumber = aadhaarNumber;
        this.wishes = wishes;
    }

    public List<String> getWishes() {
        return wishes;
    }

}
