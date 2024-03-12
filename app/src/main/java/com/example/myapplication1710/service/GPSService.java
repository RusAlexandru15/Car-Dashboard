package com.example.myapplication1710.service;

import android.annotation.SuppressLint;
import android.location.Location;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Objects;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class GPSService {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView latView, longView;


    private double segLatitude;
    private double segLongitude;





    //constructor
    public GPSService(FusedLocationProviderClient fusedLocationClient) {
        this.fusedLocationClient = fusedLocationClient;

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000); // Update interval in milliseconds

        //pentru segmentare
        this.segLatitude=0;
        this.segLongitude=0;



        locationCallback = new LocationCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationResult(LocationResult locationResult) {
                // latView.setText("No GPS"); null ptr frate
                if (locationResult != null) {

                    Location location = locationResult.getLastLocation();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();


                    //aflare viteza pentru warning
                    double speed =0;

                    if(location.hasSpeed()) {
                        speed = location.getSpeed(); //in m/s
                        speed =3.6* speed; //in km/h
                    }

                    // Formatare speed
                    DecimalFormat df = new DecimalFormat("#.###");
                    String f_speed =  df.format( speed);


                    if(latView!=null && longView!=null ) {
                        //afisez pe ecran
                        latView.setText(String.valueOf(latitude));
                        longView.setText(String.valueOf(longitude));

                        //pentru segmentare le poti folosi in orice  metoda
                        segLatitude=latitude;
                        segLongitude=longitude;


                        //update RULES ZONE


                    }
                }
            }
        };

        startLocationUpdates();
    }




    public void setLatView(TextView latView) {
        this.latView = latView;
    }

    public void setLongView(TextView longView) {
        this.longView = longView;
    }





    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }



}
