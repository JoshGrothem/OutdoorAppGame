package com.example.outdoorapp;

public class MyCoordinates {
    public double lat, longi;

    public MyCoordinates(double latitude, double longitude) {
        lat = latitude;
        longi = longitude;
    }

    /*
    Returns distance in meters
     */
    public double distanceTo(MyCoordinates dest) {
        double latDiff = lat-dest.lat;
        double longDiff = longi-dest.longi;

       latDiff *= 111320;
       longDiff *= (40075000 * Math.cos(lat)/360);

       return Math.sqrt(latDiff*latDiff + longDiff*longDiff);
    }
}
