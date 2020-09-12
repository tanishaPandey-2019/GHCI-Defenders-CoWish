package com.ghci20.codeathon.cowish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ghci20.codeathon.cowish.R;
import com.ghci20.codeathon.cowish.userlogin.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.ghci20.codeathon.cowish.constants.FIREBASE_USERS;

public class RegisterActivity extends AppCompatActivity {
    UserInfo myuser;
    EditText name;
    EditText aadhaarNumber;
    EditText mobileNumber;
    EditText location;
    EditText password;
    Button registerButton;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        aadhaarNumber = findViewById(R.id.aadhaarNumber);
        mobileNumber = findViewById(R.id.mobileNumber);
        location = findViewById(R.id.location);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserDetails();
            }
        });
    }

    private void setUserDetails() {
        myuser = new UserInfo();
        myuser.name = name.getText().toString();
        myuser.aadhaarNumber = Integer.parseInt(aadhaarNumber.getText().toString());
        myuser.mobileNumber = Integer.parseInt(mobileNumber.getText().toString());
        myuser.location = location.getText().toString();
        myuser.password = password.getText().toString();
        saveUserInFirebase(myuser);
    }

    private void saveUserInFirebase(UserInfo user) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference(FIREBASE_USERS);
            userRef.push().setValue(user);
            Log.i(TAG, "User inserted in Firebase successfully");

            // finish() method below closes the opened activity. So we are closing the register activity after the user finishes resgisteration.
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Exception encountered", e);
        }
    }

}