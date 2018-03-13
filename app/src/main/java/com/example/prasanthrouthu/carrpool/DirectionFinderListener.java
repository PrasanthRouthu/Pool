package com.example.prasanthrouthu.carrpool;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;
import com.example.prasanthrouthu.carrpool.Route;

/**
 * Created by prasanthrouthu on 23/02/18.
 */



public interface DirectionFinderListener {
    void onDirectionFinderStart();

    void onDirectionFinderSuccess(List<Route> route);
}