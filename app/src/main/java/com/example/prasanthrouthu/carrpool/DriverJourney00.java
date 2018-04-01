package com.example.prasanthrouthu.carrpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class DriverJourney00 extends AppCompatActivity {
    public static final String Firebase_Server_URL = "https://carrpool-74a90.firebaseio.com/";
    Firebase firebase;
    private Button mNext;
    private EditText Name,Date,Time;
    private String NameS,DateS,TimeS,User;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(DriverJourney00.this);
        setContentView(R.layout.activity_driver_journey00);
        Name=(EditText)findViewById(R.id.editText9);
        Date=(EditText)findViewById(R.id.editText11);
        Time=(EditText)findViewById(R.id.editText12);
        mNext = (Button) findViewById(R.id.button4);

        User = getIntent().getExtras().getString("User");
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameS = Name.getText().toString();
                DateS = Date.getText().toString();
                TimeS = Time.getText().toString();
                if (NameS.isEmpty()) {
                    Toast.makeText(DriverJourney00.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (DateS.isEmpty()) {
                    Toast.makeText(DriverJourney00.this, "Enter Date!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TimeS.isEmpty()) {
                    Toast.makeText(DriverJourney00.this, "Enter Time!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(DriverJourney00.this, MapsActivity.class);
                intent.putExtra("Name", NameS);
                intent.putExtra("Date", DateS);
                intent.putExtra("Time", TimeS);
                intent.putExtra("User",User);
                startActivity(intent);



            }
        });


    }
}
