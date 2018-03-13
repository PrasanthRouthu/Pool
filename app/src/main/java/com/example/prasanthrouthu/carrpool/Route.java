package com.example.prasanthrouthu.carrpool;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
/**
 * Created by prasanthrouthu on 23/02/18.
 */

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}