package com.ghci20.codeathon.cowish.activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ghci20.codeathon.cowish.R;

import java.util.ArrayList;

public class AddWishActivity extends AppCompatActivity {

    Button addButton;
    ListView wishList;
    ArrayList<String> stringArrayList;
    EditText wish;
    ArrayAdapter<String> stringArrayAdapter;

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
                    stringArrayList.add(wish.getText().toString());
                    stringArrayAdapter.notifyDataSetChanged();
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
}