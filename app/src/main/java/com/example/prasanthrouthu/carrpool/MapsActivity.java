package com.example.prasanthrouthu.carrpool;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.io.UnsupportedEncodingException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import com.example.prasanthrouthu.carrpool.DirectionFinder;
import com.example.prasanthrouthu.carrpool.DirectionFinderListener;
import com.example.prasanthrouthu.carrpool.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private Button btnFindPath, StartJourney;
    private EditText etOrigin;
    private EditText etDestination;
    private String SourceHolder, DestinationHolder;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private String User;
    public static final String Firebase_Server_URL = "https://carrpool-74a90.firebaseio.com/";
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Firebase.setAndroidContext(MapsActivity.this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        StartJourney = (Button) findViewById(R.id.button2);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etDestination = (EditText) findViewById(R.id.etDestination);
        firebase = new Firebase(Firebase_Server_URL);
        User = getIntent().getExtras().getString("User");



        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }

        });
        StartJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }

        });

    }

    private void sendRequest() {
        String SourceHolder = etOrigin.getText().toString();
        String DestinationHolder = etDestination.getText().toString();

        if (SourceHolder.isEmpty()) {
            Toast.makeText(this, "Enter Source!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (DestinationHolder.isEmpty()) {
            Toast.makeText(this, "Enter Destination!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new DirectionFinder(this, SourceHolder, DestinationHolder).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    private void send(){
        String SourceHolder = etOrigin.getText().toString();
        String DestinationHolder = etDestination.getText().toString();
        if (SourceHolder.isEmpty()) {
            Toast.makeText(this, "Enter Source!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (DestinationHolder.isEmpty()) {
            Toast.makeText(this, "Enter Destination!", Toast.LENGTH_SHORT).show();
            return;
        }


        //push creates a unique id in database
        Driver driver = new Driver();
        /*driver.setDriverSource(SourceHolder);
        driver.setDriverDestination(DestinationHolder);
        firebase.child("Driver").setValue(driver);*/
        Intent intent = new Intent(this, DriverJourney0.class);
        intent.putExtra("User",User);
        intent.putExtra("Source", SourceHolder);
        intent.putExtra("Destination", DestinationHolder);
        startActivity(intent);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mvgr = new LatLng(18.0602, 83.4056);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mvgr, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("MVGR College of Engineering")
                .position(mvgr)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);
            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}