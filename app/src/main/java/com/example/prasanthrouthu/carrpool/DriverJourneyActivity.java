package com.example.prasanthrouthu.carrpool;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class DriverJourneyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button Choose;
    private Button Upload;
    private EditText editText;
    private ImageView imageView;
    private String User;


    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    ProgressDialog pd;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_journey);

        Choose = (Button) findViewById(R.id.selectImage);
        Upload = (Button) findViewById(R.id.uploadTask);
        editText = (EditText) findViewById(R.id.editText);

        imageView  = (ImageView) findViewById(R.id.imageView2);
        User = getIntent().getExtras().getString("User");

        Choose.setOnClickListener(this);
        Upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == Choose){
            showFileChooser();
        }else if(view == Upload){
            pd=new ProgressDialog(this);
            pd.setCancelable(false);
            pd.show();
            uploadImage();
        }
    }

    private void uploadImage() {
        if(file!=null)
        {
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference reference=storage.getReferenceFromUrl("gs://carrpool-74a90.appspot.com/");
            StorageReference imagesRef=reference.child("images/"+editText.getText().toString());
            UploadTask uploadTask = imagesRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(DriverJourneyActivity.this, "Error : "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Enter your Name!", Toast.LENGTH_SHORT).show();
                        return;}
                        else {
                        Toast.makeText(DriverJourneyActivity.this, "Uploading Done!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DriverJourneyActivity.this, MapsActivity.class);
                        intent.putExtra("User",User);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            file = data.getData();

            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file);
                //Setting the Bitmap to ImageView
                //imageView.setImageBitmap(bitmap);
                imageView.setImageURI(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}