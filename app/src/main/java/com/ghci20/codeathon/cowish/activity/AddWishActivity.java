package com.ghci20.codeathon.cowish.activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


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

public class AddWishActivity extends AppCompatActivity {

    private static final String TAG = "AddWishActivity";
    Button addButton;
    ListView wishListView;
    ArrayList<String> wishList;
    EditText wish;
    ArrayAdapter<String> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        addButton = findViewById(R.id.addButton);
        wishListView = findViewById(R.id.wishList);
        wish = findViewById(R.id.wish);

        wishList = new ArrayList<>();
        ArrayList<String> wishListFromFirebase = Util.getWishListFromSharedPref(getApplicationContext());
        if(wishListFromFirebase != null ) {
            wishList.addAll(wishListFromFirebase);
        }
        for (int i = 0; i < 2; i++) {
            stringArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, wishList);
            wishListView.setAdapter(stringArrayAdapter);

            //adding wish to the wish list
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wishList.add(wish.getText().toString());
                    stringArrayAdapter.notifyDataSetChanged();
                    addWishToFireBase();
                    wish.getText().clear();
                }
            });

            //show what item is selected
            wishListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), "You clicked item name :" + stringArrayAdapter.getItem(position), Toast.LENGTH_LONG).show();
                }
            });

            wishListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                    wishList.remove(which_item);
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
        final int sharedPrefAadhaarNumber = Util.getAadhaarNumberFromSharedPref(getApplicationContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference wishesRef = database.getReference(FIREBASE_WISHES);
        Query query = wishesRef
                .orderByChild("aadhaarNumber")
                .equalTo(sharedPrefAadhaarNumber);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    WishesInfo wishesInfo = new WishesInfo(sharedPrefAadhaarNumber, wishList);
                    wishesRef.push().setValue(wishesInfo);
                    return;
                }

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    updateWishList(wishesRef, dataSnapshot1.getKey(), sharedPrefAadhaarNumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Encountered an error ", error.toException());
            }
        });
    }

    private void updateWishList(DatabaseReference wishRef, String key, int aadhaarNumber) {
        WishesInfo wishesInfo = new WishesInfo(aadhaarNumber, wishList);
        Map<String, Object> postUpdatedValues = wishesInfo.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, postUpdatedValues);
        wishRef.updateChildren(childUpdates);
    }
}