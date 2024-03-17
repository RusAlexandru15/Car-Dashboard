package com.example.myapplication1710.service;

import android.annotation.SuppressLint;
import android.location.Location;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;


import com.example.myapplication1710.R;
import com.example.myapplication1710.electronic_horizon.Road;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class GPSService {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView latView, longView,segView;
    private ImageView slView;


    private Road currentRoad;
    private double segLatitude;
    private double segLongitude;






    //constructor
    public GPSService(FusedLocationProviderClient fusedLocationClient,ImageView slView) {
        this.fusedLocationClient = fusedLocationClient;
        this.slView = slView;

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
                    int ego_speed_i =0;


                    if(location.hasSpeed()) {
                        speed = location.getSpeed(); //in m/s
                        speed =3.6* speed; //in km/h
                        ego_speed_i=(int)speed;
                    }




                    if(latView!=null && longView!=null && segView!=null ) {
                        //afisez pe ecran
                        latView.setText(String.valueOf(latitude));
                        longView.setText(String.valueOf(longitude));

                        //pentru segmentare le poti folosi in orice  metoda
                        segLatitude=latitude;
                        segLongitude=longitude;


                        //update RULES ZONE am obiectul ROAD de care ma folosesc
                        if(currentRoad != null){
                            int current_segment_id=currentRoad.findCurrentSegment(segLatitude,segLongitude);
                            int current_speed_limit =currentRoad.findCurrentSpeedLimit();
                            int current_speed_offset=ego_speed_i -current_speed_limit;

                            //PROVIZORIU
                            //in loc de segView urmeaza imageView
                            segView.setText(String.valueOf(current_segment_id));


                            //if  current_segment_id != -1 .. cu switch-urile
                            updateSLRule(current_segment_id,current_speed_limit,current_speed_offset);

                            //PT TESTARE
                            //updateSLRule(0,50,70);


                        }
                    }
                }
            }
        };

        startLocationUpdates();
    }


    public void updateSLRule(int current_segment_id,int current_speed_limit,int current_speed_offset){
        if (current_segment_id != -1) {
            if (current_speed_offset < 10)
                switch (current_speed_limit) {
                    case 30:
                        slView.setImageResource(R.drawable.sll_30_normal);
                        break;
                    case 50:
                        slView.setImageResource(R.drawable.sll_50_normal);
                        break;
                    case 60:
                        slView.setImageResource(R.drawable.sll_60_normal);
                        break;
                    case 70:
                        slView.setImageResource(R.drawable.sll_70_normal);
                        break;
                    case 90:
                        slView.setImageResource(R.drawable.sll_90_normal);
                        break;
                    case 100:
                        slView.setImageResource(R.drawable.sll_100_normal);
                        break;
                    case 130:
                        slView.setImageResource(R.drawable.sll_130_normal);
                        break;
                    default:
                        break;
                }
            else
                switch (current_speed_limit) {
                    case 30:
                        slView.setImageResource(R.drawable.sll_30_warning);
                        break;
                    case 50:
                        slView.setImageResource(R.drawable.sll_50_warning);
                        break;
                    case 60:
                        slView.setImageResource(R.drawable.sll_60_warning);
                        break;
                    case 70:
                        slView.setImageResource(R.drawable.sll_70_warning);
                        break;
                    case 90:
                        slView.setImageResource(R.drawable.sll_90_warning);
                        break;
                    case 100:
                        slView.setImageResource(R.drawable.sll_100_warning);
                        break;
                    case 130:
                        slView.setImageResource(R.drawable.sll_130_warning);
                        break;
                    default:
                        break;
                }
        }
    }


    public void setLatView(TextView latView) {
        this.latView = latView;
    }

    public void setLongView(TextView longView) {
        this.longView = longView;
    }

    public void setSegView(TextView segView){this.segView=segView;}

    public void setCurrentRoad(Road road){this.currentRoad=road;}

    public void setSlView(ImageView slView) {
        this.slView = slView;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }



}
