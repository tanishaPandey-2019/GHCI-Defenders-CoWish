package com.ghci20.codeathon.cowish.userlogin;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInfo {

    public String name;
    public int aadhaarNumber;
    public int mobileNumber;
    public String location;
    public String password;

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInfo(String name, int aadhaarNumber,
                    int mobileNumber, String location,
                    String password) {
        this.name = name;
        this.aadhaarNumber = aadhaarNumber;
        this.mobileNumber = mobileNumber;
        this.location = location;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getAadhaarNumber() {
        return aadhaarNumber;
    }

    public int getmobileNumber() {
        return mobileNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getPassword() {
        return password;
    }

}
