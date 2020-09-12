package com.ghci20.codeathon.cowish.activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ghci20.codeathon.cowish.R;
import com.ghci20.codeathon.cowish.WishesInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.ghci20.codeathon.cowish.constants.FIREBASE_USERS;
import static com.ghci20.codeathon.cowish.constants.FIREBASE_WISHES;
import static com.ghci20.codeathon.cowish.constants.SHARED_PREF_AADHAAR_NUMBER;

public class AddWishActivity extends AppCompatActivity {

    Button addButton;
    ListView wishList;
    ArrayList<String> stringArrayList;
    EditText wish;
    ArrayAdapter<String> stringArrayAdapter;
    List<String> myWishes;
    private static final String TAG = "AddWishActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        addButton = (Button) findViewById(R.id.addButton);
        wishList = (ListView) findViewById(R.id.wishList);
        wish = (EditText) findViewById(R.id.wish);

        stringArrayList = new ArrayList<>();
        for(int i=0; i<2; i++){
            stringArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
            wishList.setAdapter(stringArrayAdapter);

         //adding wish to the wish list
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myWishes.add(wish.getText().toString());
                    stringArrayList.add(wish.getText().toString());
                    stringArrayAdapter.notifyDataSetChanged();
                    addWishToFireBase();
                    wish.getText().clear();
                }
            });

         //show what item is selected

            wishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "You clicked item name :" + stringArrayAdapter.getItem(position),Toast.LENGTH_LONG) .show();
                }
            });

            wishList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    final int which_item = position;

                    new AlertDialog.Builder(AddWishActivity.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are you sure ?")
                            .setMessage("Do you want to delete this wish")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stringArrayList.remove(which_item);
                                    stringArrayAdapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();




                    return true;
                }
            });


        }
    }

    private void addWishToFireBase() {
        int sharedPrefAadhaarNumber = getApplicationContext().getSharedPreferences("default", Context.MODE_PRIVATE)
                .getInt(SHARED_PREF_AADHAAR_NUMBER, 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference wishesRef = database.getReference(FIREBASE_WISHES);
        WishesInfo wishesInfo = new WishesInfo(sharedPrefAadhaarNumber, stringArrayList);
        wishesRef.push().setValue(wishesInfo);
        Log.i(TAG, "User inserted in Firebase successfully");

//        DatabaseReference wishesRef = database.getReference(FIREBASE_WISHES);

    }
}