package com.example.outdoorapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class MainActivity extends AppCompatActivity {
    //balloons
    private BalloonRunner balloonRunner;
    private BalloonView balloonView;

    //location
    private FusedLocationProviderClient locationProvider;
    private MyCoordinates poiLocation;
    private boolean foundThePOI;
    private TextView coordsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBalloonStuff();
        setupLocationStuff();

        //Thread balloonThread = new Thread(balloonRunner);
        //balloonThread.start();
    }

    private void setupBalloonStuff() {
        foundThePOI = false;

        DisplayMetrics screenMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenMetrics);
        balloonView = new BalloonView(this, screenMetrics);

        ConstraintLayout balloonContainer = findViewById(R.id.container);
        balloonContainer.addView(balloonView);

        Handler balloonHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(android.os.Message msg) {
                balloonView.invalidate();;
            }
        };

        balloonRunner = new BalloonRunner(balloonHandler, balloonView.getBalloons());
    }


    private void setupLocationStuff() {
        poiLocation = new MyCoordinates(34.06417263426627, -117.16137558750135);

        coordsView = findViewById(R.id.coordsView);

        locationProvider = LocationServices.getFusedLocationProviderClient(this);

        int finePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (finePermission != PackageManager.PERMISSION_GRANTED) {
            //handled below in onRequestPermissionsResult()
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        continueLocationSetup();
    }


    @SuppressLint("MissingPermission")
    private void continueLocationSetup() {
        LocationRequest.Builder requestBuilder = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500);
        requestBuilder.setMinUpdateIntervalMillis(250);
        requestBuilder.setMaxUpdateDelayMillis(1000);
        requestBuilder.setGranularity(Granularity.GRANULARITY_FINE);

        LocationRequest locationRequest = requestBuilder.build();

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                MyCoordinates currCoords = new MyCoordinates(location.getLatitude(), location.getLongitude());
                double distToPOI = currCoords.distanceTo(poiLocation);
                distToPOI = Math.round(distToPOI);
                if (distToPOI<10) {
                    coordsView.setText("You found the echo chamber!");
                    if (!foundThePOI) {
                        Thread balloonThread = new Thread(balloonRunner);
                        balloonThread.start();
                    }
                    foundThePOI = true;
                }
                else {
                    coordsView.setText("Distance to echo chamber (not guaranteed to be 100% accurate): " + distToPOI);
                }
            }
        };

        locationProvider.requestLocationUpdates(locationRequest, locationListener, getMainLooper());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions) {
            if (permission.equals("android.permission.ACCESS_FINE_LOCATION")) {
                continueLocationSetup();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}