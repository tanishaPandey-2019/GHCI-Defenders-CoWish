package com.ghci20.codeathon.cowish.activity;


import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    private static GrantWishActivity inst;
    ArrayList<String> smsMessageList = new ArrayList<String>();
//    ArrayAdapter arrayAdapter;
public static GrantWishActivity instance() { return inst; }


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
                    new AlertDialog.Builder(GrantWishActivity.this)
                            .setIcon(android.R.drawable.sym_action_call)
                            .setTitle("User details")
                            .setMessage("User : John\nMobile : 9999999999\nLocation : Mumbai")
                            .show();
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

        handleSMSWishes();
    }

    private void handleSMSWishes() {
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            refreshSmsInbox();
        }

        else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(GrantWishActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
//        stringArrayAdapter.clear();
        do {
            String str = smsInboxCursor.getString(indexBody) + "\n";
            stringArrayAdapter.add(str);
        } while (smsInboxCursor.moveToNext());
    }

    public void updateList(final String smsMessage) {
        stringArrayAdapter.insert(smsMessage, 0);
        stringArrayAdapter.notifyDataSetChanged();
    }

}