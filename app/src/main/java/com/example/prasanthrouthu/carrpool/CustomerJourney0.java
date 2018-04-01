package com.example.prasanthrouthu.carrpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class CustomerJourney0 extends AppCompatActivity {
    private Button mSubmit;
    private String SourceHolder,DestinationHolder,User;
    public static final String Firebase_Server_URL = "https://carrpool-74a90.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_journey0);
        User = getIntent().getExtras().getString("User");
        mSubmit = (Button) findViewById(R.id.button3);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerJourney0.this, CustomerJourney00.class);
                intent.putExtra("User",User);
                startActivity(intent);
                //finish();
                //return;
            }
        });
    }
}
