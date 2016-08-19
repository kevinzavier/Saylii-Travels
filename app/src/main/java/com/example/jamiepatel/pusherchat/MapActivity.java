package com.example.jamiepatel.pusherchat;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;


/**
 * Created by kevin on 8/16/16.
 */
public class MapActivity extends Activity{
    Button chat;
    Button addfriends;
    Button events;
    Button go;
    Button more;
    double longitude;
    double latitude;


    MapView mapView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //I use LocationManager to get the last saves location
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        chat = (Button) findViewById(R.id.button3);

        //over here i implement LocationListener to check for changes
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
            public void onStatusChanged(String provider, int status, Bundle extras){}
            public void onProviderDisabled(String provider){}
            public void onProviderEnabled(String provider){}

        };
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);


        //Below is where I try to set the Camera
        //TODO this part is not working, the camera is still pointed to New York (that is what I set it to in the xml file)
        LatLng myLatLng = new LatLng(latitude, longitude);
        CameraPosition.Builder cam = new CameraPosition.Builder();
        cam.target(myLatLng);



        Toast.makeText(MapActivity.this, String.valueOf(latitude) + "---" + String.valueOf(longitude), Toast.LENGTH_LONG).show();
        Log.i("THE LAT IS HERE", String.valueOf(latitude));
        Log.i("THE LONG IS HERE", String.valueOf(longitude));



    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
