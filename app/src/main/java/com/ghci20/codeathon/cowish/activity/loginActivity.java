package com.ghci20.codeathon.cowish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ghci20.codeathon.cowish.R;


public class loginActivity extends AppCompatActivity {

    //private static final String TAG = "loginActivity" ;
//    Button loginButton;
//    EditText loginAadhaar;
//    EditText loginPass;


    EditText LoginAadhaar, loginPass;
    TextView askRegistration;
    Button loginButton;

    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.login_layout);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginAadhaar = (EditText) findViewById(R.id.LoginAadhar);
        loginPass = (EditText) findViewById(R.id.loginPass);
        loginButton = (Button) findViewById(R.id.loginButton);
        askRegistration = (TextView) findViewById(R.id.askRegisteration);

        askRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                    Intent i = new Intent(loginActivity.this,
                            RegisterActivity.class);
                    startActivity(i);
            }

        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });










//        Log.i(TAG, "In onCreate");
//        loginAadhaar = findViewById(R.id.LoginAadhar);
//        loginPass = findViewById(R.id.loginPass);
//        loginButton = findViewById(R.id.loginButton);
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                Log.i(TAG, "database = " + database.toString());
//                DatabaseReference userRef =  database.getReference("Users");
//                Log.i(TAG, "database = " + userRef.toString());
//
//                UserInfo myUser = new UserInfo("Ishita", 1111111111, 9999999, "Delhi", "Pass1");
//                UserInfo myUser1 = new UserInfo("Mini", 14677, 4566, "NYC", "Pass2");
//                UserInfo myUser2 = new UserInfo("Tanisha", 11167881, 765, "Amsterdam", "Pass3");
//                UserInfo myUser3 = new UserInfo("SKP", 789911111, 344, "Thailand", "Pass4");
//                UserInfo myUser4 = new UserInfo("Flower", 56111111, 7789, "Garden", "Pass5");
//                userRef.push().setValue(myUser);
//                userRef.push().setValue(myUser1);
//                userRef.push().setValue(myUser2);
//                userRef.push().setValue(myUser3);
//                userRef.push().setValue(myUser4);
//
//            }
//        });
    }
}
