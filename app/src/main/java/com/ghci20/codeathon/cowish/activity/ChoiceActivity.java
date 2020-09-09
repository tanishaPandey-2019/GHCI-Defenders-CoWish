package com.ghci20.codeathon.cowish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ghci20.codeathon.cowish.R;

import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {

    Button haveButton;
    Button grantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        //opening add wish list page on clicking have button
        haveButton = (Button) findViewById(R.id.haveButton);
        haveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddWishActivity();
            }
        });

        //opening sms wish list page on clicking grant button
        grantButton = (Button) findViewById(R.id.grantButton);
        grantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensms();
            }
        });



    }

    public void openAddWishActivity(){
        Intent intent = new Intent(this, AddWishActivity.class);
        startActivity(intent);
    }

    public void opensms(){
        Intent intent = new Intent(this, sms.class);
        startActivity(intent);
    }

}