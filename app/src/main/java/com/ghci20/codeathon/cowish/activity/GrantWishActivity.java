package com.ghci20.codeathon.cowish.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ghci20.codeathon.cowish.R;
import com.ghci20.codeathon.cowish.Util;
import com.ghci20.codeathon.cowish.WishesInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ghci20.codeathon.cowish.constants.FIREBASE_WISHES;

public class GrantWishActivity extends AppCompatActivity {

    private static final String TAG = "GrantWishActivity";
    ListView allWishListView;
    ArrayList<String> allWishList;
    ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grant_wish);

        allWishListView = findViewById(R.id.viewAllWishList);
        allWishList = new ArrayList<>();
        ArrayList<String> wishListFromFirebase = Util.getAllWishListFromSharedPref(getApplicationContext());
        if (wishListFromFirebase != null) {
            allWishList.addAll(wishListFromFirebase);
        }
        for (int i = 0; i < 2; i++) {
            stringArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, allWishList);
            allWishListView.setAdapter(stringArrayAdapter);

            //show what item is selected
            allWishListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "You clicked item name :" + stringArrayAdapter.getItem(position), Toast.LENGTH_LONG).show();
                }
            });

            allWishListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final int which_item = position;
                    new AlertDialog.Builder(GrantWishActivity.this)
                            .setIcon(android.R.drawable.ic_delete)
                            .setTitle("Are you sure ?")
                            .setMessage("Do you want to delete this wish")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allWishList.remove(which_item);
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

}