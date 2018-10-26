package com.odetocode.onetaphelp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.HttpsURLConnection;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private ArrayList<Marker> list = new ArrayList<>();
    private LocationListener listener;
    ArrayList<com.google.android.gms.maps.model.Marker> markerList = new ArrayList<>();
    private static com.google.android.gms.maps.model.Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mapFragment.getMapAsync(this);
        listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                Log.i("App", "abcdf");
                if (mMap != null)
                {
                    if (marker == null)
                    {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("You are here")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        markerList.add(marker);
                    }
                    else
                    {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        marker.setPosition(latLng);
                    }
                    for (int i = 0;i < markerList.size();i++)
                    {
                        markerList.get(markerList.size()-1).remove();
                        markerList.remove(markerList.size()-1);
                    }
                    String url = "https://onetaphelpbackend.azurewebsites.net/api/values";
                    RequestQueue ExampleRequestQueue = Volley.newRequestQueue(MapsActivity.this);
                    StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            Log.i("App", "Got response " + response);
                            String getdata = response;

                            if (getdata != null)
                            {
                                Log.d("app", "not null");
                                Log.d("app", getdata);

                                String[] aa = getdata.split("@");

                                for (int i = 0; i < aa.length; i++)
                                {
                                    String bb[] = aa[i].split("_");
                                    long a, b, c, d;
                                    a = b = c = d = 0;
                                    Log.i("App", aa[i]);/*
                        for (int ii = 0;ii < bb.length;ii++)
                        {
                            Log.i("App",bb[ii]);
                        }*/
                                    try
                                    {
                                        a = Long.parseLong(bb[0]);
                                        b = Long.parseLong(bb[1]);
                                        c = Long.parseLong(bb[2]);
                                        d = Long.parseLong(bb[3]);
                                    }
                                    catch (Exception e)
                                    {

                                    }
                                    list.add(new Marker(a, b, c, d));
                                }
                                for (int j = 0; j < list.size(); j++)
                                {
                                    double y = (list.get(j).latitude) / 100000000.0;
                                    double x = (list.get(j).longitude) / 100000000.0;
                                    LatLng sydne = new LatLng(x, y);
                                    Log.i("App", " " + x + "$" + y);
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(x, y))
                                            .title(list.get(j).number + " ")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                                }
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener()
                    { //Create an error listener to handle errors appropriately.
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            //This code is executed if there is an error.
                            Log.i("App", "ERROR VOLLEY");
                        }
                    });

                    ExampleRequestQueue.add(ExampleStringRequest);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle)
            {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        }
        else
        {
            Console.print("Permission for location");
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        String azure = "http://onetaphelpbackend.azurewebsites.net/api/values";


RequestLocation();

    }
    void RequestLocation()
    {

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        String url = "https://onetaphelpbackend.azurewebsites.net/api/values";
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("App","Got response "+response);
                String getdata = response;

                if (getdata != null){
                    Log.d("app","not null");
                    Log.d("app",getdata);

                    String[] aa = getdata.split("@");

                    for (int i=0;i<aa.length;i++)
                    {
                        String bb[] = aa[i].split("_");
                        long a,b,c,d;
                        a = b =c =d =0;
                        Log.i("App",aa[i]);/*
                        for (int ii = 0;ii < bb.length;ii++)
                        {
                            Log.i("App",bb[ii]);
                        }*/
                        try
                        {
                            a = Long.parseLong(bb[0]);
                            b = Long.parseLong(bb[1]);
                            c = Long.parseLong(bb[2]);
                            d = Long.parseLong(bb[3]);
                        }
                        catch (Exception e)
                        {

                        }
                        list.add(new Marker(a,b,c,d));
                    }
                    for(int j=0;j<list.size();j++)
                    {
                        double y = (list.get(j).latitude)/100000000.0;
                        double x = (list.get(j).longitude)/100000000.0;
                        LatLng sydne = new LatLng(x,y) ;
                        Log.i("App", " " + x+"$" + y );
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng( x,y))
                                .title(list.get(j).number + " ")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.i("App","ERROR VOLLEY");
            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);
    }
}
