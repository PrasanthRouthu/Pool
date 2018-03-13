package com.example.prasanthrouthu.carrpool;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;


import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class DriverLoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button mRegistration, mLogin;
    private String EmailHolder,PasswordHolder,User;
    public static final String Firebase_Server_URL = "https://carrpool-74a90.firebaseio.com/";
    private ProgressDialog pd;
    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(DriverLoginActivity.this);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_driver_login);
        mEmail = (EditText) findViewById(R.id.editText3);
        mPassword = (EditText) findViewById(R.id.editText2);
        mRegistration = (Button) findViewById(R.id.button);
        mLogin = (Button) findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();
        EmailHolder = mEmail.getText().toString().trim();
        PasswordHolder = mPassword.getText().toString().trim();
        firebase = new Firebase(Firebase_Server_URL);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String EmailHolder = mEmail.getText().toString().trim();
                String PasswordHolder = mPassword.getText().toString().trim();
                final String User = EmailHolder.substring(0, EmailHolder.indexOf("."));
                if (TextUtils.isEmpty(EmailHolder)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(PasswordHolder)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PasswordHolder.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                pd=new ProgressDialog(DriverLoginActivity.this);
                pd.setCancelable(false);
                pd.show();
                auth.createUserWithEmailAndPassword(EmailHolder, PasswordHolder)
                        .addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(DriverLoginActivity.this, "Registration failed" ,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(DriverLoginActivity.this, DriverJourneyActivity.class);
                                    intent.putExtra("User",User);
                                    startActivity(intent);

                                }
                            }
                        });
            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String User = email.substring(0, email.indexOf("."));
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                pd=new ProgressDialog(DriverLoginActivity.this);
                pd.setCancelable(false);
                pd.show();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(DriverLoginActivity.this, "Enter valid details!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    Intent intent = new Intent(DriverLoginActivity.this, DriverJourneyActivity.class);
                                    intent.putExtra("User",User);
                                    startActivity(intent);

                                }
                            }
                        });
            }
        });
    }
}

