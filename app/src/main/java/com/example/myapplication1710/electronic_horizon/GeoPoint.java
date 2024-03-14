package com.example.myapplication1710.electronic_horizon;

import com.google.gson.annotations.SerializedName;

public class GeoPoint {

    @SerializedName("latitude")
    protected double latitude;

    @SerializedName("longitude")
    protected double longitude;

    //constructor
    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "GP" + latitude + "," + longitude ;
    }

}
