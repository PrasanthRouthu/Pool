package com.example.prasanthrouthu.carrpool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
public class CustomerJourney00 extends AppCompatActivity {

    Firebase myFirebase;
    FirebaseDatabase firebaseDatabase;
    ListView listview;
    private Button mAccept,mCancel;
    long id;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_journey00);
        Firebase.setAndroidContext(this);
        myFirebase = new Firebase("https://carrpool-74a90.firebaseio.com/Driver");
        //firebaseDatabase.getReference().child("Driver");
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(myArrayAdapter);
        myFirebase.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                //String myChildValues = String.valueOf(dataSnapshot.getValue());
                String myKey = String.valueOf(dataSnapshot.getKey());
                //myChildValues = myChildValues.replaceAll("[{}]", "");
                myKey = myKey.replaceAll("[{}]", "");
                list.add(myKey);
                //list.add(myChildValues);
                myArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                myArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String product = ((TextView) view).getText().toString();
                // Launching new Activity on selecting single List Item
                Intent i = new Intent(CustomerJourney00.this, CustomerRequest.class);
                i.putExtra("product", product);
                i.putExtra("id",id);
                startActivity(i);

            }
        });


        }
}