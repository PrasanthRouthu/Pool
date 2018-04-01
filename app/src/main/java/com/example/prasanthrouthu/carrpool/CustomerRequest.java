package com.example.prasanthrouthu.carrpool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
public class CustomerRequest extends AppCompatActivity{
    ListView listView;
    Firebase myFirebase;
    FirebaseDatabase firebaseDatabase;
    Button mAccept,mCancel;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> location = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Double latitude,longitude;
    String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_request);
        UserName = getIntent().getStringExtra("product");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Driver").child(UserName);


        listView = (ListView) findViewById(R.id.listview1);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value=dataSnapshot.getValue(String.class);
                arrayList.add(value);
                arrayAdapter = new ArrayAdapter<String>(CustomerRequest.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(arrayAdapter);

                /*String myChildValues = String.valueOf(dataSnapshot.getValue());
                myChildValues = myChildValues.replaceAll("[{}]", "");
                arrayList.add(myChildValues);*/



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference = firebaseDatabase.getReference("DriverLocation").child(UserName).child("l");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Double value1=dataSnapshot.getValue(Double.class);
                String value=Double.toString(value1);
                location.add(value);
                Log.d(value, "value: "+value);
                String latlong[]=location.toArray(new String[location.size()]);
                Double latitude = Double.parseDouble(latlong[0]);
                Double longitude = Double.parseDouble(latlong[1]);

               /*arrayAdapter = new ArrayAdapter<String>(CustomerRequest.this, android.R.layout.simple_list_item_1, arrayList);

                listView.setAdapter(arrayAdapter);*/

                /*String myChildValues = String.valueOf(dataSnapshot.getValue());
                myChildValues = myChildValues.replaceAll("[{}]", "");
                arrayList.add(myChildValues);*/



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        mAccept = (Button) findViewById(R.id.button5);
        mCancel = (Button) findViewById(R.id.button6);

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerRequest.this, DriverLoginActivity.class);
                startActivity(intent);
                //finish();
                //return;
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerRequest.this, CustomerJourney00.class);
                startActivity(intent);
                // finish();
                //return;
            }
        });

    }

}

