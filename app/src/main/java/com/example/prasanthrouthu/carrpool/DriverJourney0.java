package com.example.prasanthrouthu.carrpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import javax.xml.transform.Source;


public class DriverJourney0 extends AppCompatActivity {
    private Button mSubmit;
    private EditText CarModel,CarNumber,Seats,PhoneNumber,Rate;
    private String CarModelS,CarNumberS,SeatsS,PhoneNumberS,RateS,SourceHolder,DestinationHolder,User,Name,Date,Time;
    public static final String Firebase_Server_URL = "https://carrpool-74a90.firebaseio.com/";
    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(DriverJourney0.this);
        setContentView(R.layout.activity_driver_journey0);
        CarModel = (EditText)findViewById(R.id.editText4) ;
        CarNumber = (EditText)findViewById(R.id.editText5);
        Seats = (EditText)findViewById(R.id.editText6);
        PhoneNumber = (EditText)findViewById(R.id.editText8);
        Rate = (EditText)findViewById(R.id.editText7);
        mSubmit = (Button) findViewById(R.id.button3);
        firebase = new Firebase(Firebase_Server_URL);
        User = getIntent().getExtras().getString("User");
        Date = getIntent().getExtras().getString("Date");
        Time = getIntent().getExtras().getString("Time");
        Name = getIntent().getExtras().getString("Name");

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarModelS = CarModel.getText().toString();
                CarNumberS = CarNumber.getText().toString();
                SeatsS = Seats.getText().toString();
                PhoneNumberS = PhoneNumber.getText().toString();
                RateS = Rate.getText().toString();
                SourceHolder = getIntent().getExtras().getString("Source");
                DestinationHolder = getIntent().getExtras().getString("Destination");
                if (CarModelS.isEmpty()) {
                    Toast.makeText(DriverJourney0.this, "Enter Car Model!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (CarNumberS.isEmpty()) {
                    Toast.makeText(DriverJourney0.this, "Enter Car Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (SeatsS.isEmpty()) {
                    Toast.makeText(DriverJourney0.this, "Enter Number of Seats!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PhoneNumberS.isEmpty()) {
                    Toast.makeText(DriverJourney0.this, "Enter PhoneNumber!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (RateS.isEmpty()) {
                    Toast.makeText(DriverJourney0.this, "Enter Rate per KM!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PhoneNumberS.length() !=10) {
                    Toast.makeText(getApplicationContext(), "Enter Valid PhoneNumber!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Driver driver = new Driver();
                driver.setCarModelS(CarModelS);
                driver.setCarNumberS(CarNumberS);
                driver.setPhoneNumberS(PhoneNumberS);
                driver.setRateS(RateS);
                driver.setSeatsS(SeatsS);
                driver.setDriverSource(SourceHolder);
                driver.setDriverDestination(DestinationHolder);
                driver.setName(Name);
                driver.setTime(Time);
                driver.setDate(Date);
                firebase.child("Driver").child(User).setValue(driver);
                Intent intent = new Intent(DriverJourney0.this, DriverLocation.class);
                intent.putExtra("Source", SourceHolder);
                intent.putExtra("Destination", DestinationHolder);
                intent.putExtra("User",User);
                startActivity(intent);
                //finish();
                //return;
            }
        });
    }
}
