package com.ghci20.codeathon.cowish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ghci20.codeathon.cowish.R;
import com.ghci20.codeathon.cowish.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.ghci20.codeathon.cowish.constants.FIREBASE_USERS;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity" ;
    Button loginButton;
    EditText loginAadhaar;
    EditText loginPass;
    TextView askRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "In onCreate");
        loginAadhaar = findViewById(R.id.LoginAadhar);
        loginPass = findViewById(R.id.loginPass);
        loginButton = findViewById(R.id.loginButton);
        askRegistration = (TextView) findViewById(R.id.askRegisteration);

        // Open regosteration page
        askRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }

        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO : Implement password matching
                // Currently assuming that whatever username and password is entered is correct
                Util.setAadhaarNumberToSharedPref(getApplicationContext(), loginAadhaar.getText().toString());
                Util.getWishListFromFirebase(getApplicationContext());
                openChoiceActivity();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                Log.i(TAG, "database = " + database.toString());
                DatabaseReference userRef =  database.getReference(FIREBASE_USERS);
                Log.i(TAG, "database = " + userRef.toString());

//                UserInfo myUser = new UserInfo("Ishita", 1111111111, 9999999, "Delhi", "Pass1");
//                UserInfo myUser1 = new UserInfo("Mini", 14677, 4566, "NYC", "Pass2");
//                UserInfo myUser2 = new UserInfo("Tanisha", 11167881, 765, "Amsterdam", "Pass3");
//                UserInfo myUser3 = new UserInfo("SKP", 1234, 344, "Thailand", "Pass4");
//                UserInfo myUser4 = new UserInfo("Flower", 56111111, 7789, "Garden", "Pass5");
//                userRef.push().setValue(myUser);
//                userRef.push().setValue(myUser1);
//                userRef.push().setValue(myUser2);
//                userRef.push().setValue(myUser3);
//                userRef.push().setValue(myUser4);

//                Toast myToast = new Toast(MainActivity.this);
//                myToast.cancel();
//                boolean result = SignInOperations.isPasswordCorrect(Integer.parseInt(loginAadhaar.getText().toString()), loginPass.getText().toString());
//                Log.i(TAG, "Result = " + result);
//                if (result) {
//                    myToast.makeText(MainActivity.this, "CORRECT PASSWORD", Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, "Correct password");
//                } else {
//                    myToast.makeText(MainActivity.this, "INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, "Incorrect password");
//                }




            }

        });

        }



    private void openChoiceActivity(){
        Intent intent = new Intent(this, ChoiceActivity.class);
        startActivity(intent);
    }





}