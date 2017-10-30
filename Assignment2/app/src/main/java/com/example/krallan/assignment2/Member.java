package com.example.krallan.assignment2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Linus on 2017-10-06.
 */

public class Member {
    private String member;
    private double longitude;
    private double latitude;

    public Member(String member, double longitude, double latitude) {
        this.member = member;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getMember() {
        return member;
    }
    public LatLng getLatLng(){ return new LatLng(latitude,longitude);}
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
