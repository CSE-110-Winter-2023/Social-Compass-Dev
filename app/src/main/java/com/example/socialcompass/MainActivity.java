package com.example.socialcompass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    private Location targetLocation1;
    private Location targetLocation2;
    private Location targetLocation3;

    private TextView tv_location_1;
    private TextView tv_location_2;
    private TextView tv_location_3;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private int circleRadiusLayerOne;
    private final int interval_for_stacking = 10;

    //private Toast locationError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleRadiusLayerOne = (int) (180 * getResources().getDisplayMetrics().scaledDensity);
        tv_location_1 = (TextView) findViewById(R.id.home1);
        tv_location_2 = (TextView) findViewById(R.id.home2);
        tv_location_3 = (TextView) findViewById(R.id.home3);
        //locationError = Toast.makeText(this, "Please enable location permissions to use Social Compass.", Toast.LENGTH_LONG);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;

                // If a target location has been set, update the angle
                if (targetLocation1 != null && targetLocation2 != null && targetLocation3 != null) {
                    updateAngle();
                }
            }

//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//            }
        };

        // Request location updates
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                currentLocation = lastKnownLocation;
            }
        }
        // Set the target location
        targetLocation1 = new Location("Ottawa");
        targetLocation1.setLatitude(45.0819);
        targetLocation1.setLongitude(-73.3329);

        targetLocation2 = new Location("Ottawa2");
        targetLocation2.setLatitude(46.0819);
        targetLocation2.setLongitude(-72.3329);

        targetLocation3 = new Location("Ottawa3");
        targetLocation3.setLatitude(46.0819);
        targetLocation3.setLongitude(-73.3329);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation != null) {
                        currentLocation = lastKnownLocation;
                    }
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.msg_location_permissions_denied)
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, id) -> dialog.dismiss());
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }



    private void updateAngle() {
        float angle1 = currentLocation.bearingTo(targetLocation1);
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) tv_location_1.getLayoutParams();
        layoutParams1.circleAngle = angle1;
        layoutParams1.circleRadius = circleRadiusLayerOne;
        tv_location_1.setLayoutParams(layoutParams1);
        tv_location_1.setRotation(angle1);

        float angle2 = currentLocation.bearingTo(targetLocation2);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) tv_location_2.getLayoutParams();
        layoutParams2.circleAngle = angle2;
        layoutParams2.circleRadius = circleRadiusLayerOne;
        tv_location_2.setLayoutParams(layoutParams2);
        tv_location_2.setRotation(angle2);

        float angle3 = currentLocation.bearingTo(targetLocation3);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) tv_location_3.getLayoutParams();
        layoutParams3.circleAngle = angle3;
        layoutParams3.circleRadius = circleRadiusLayerOne;
        tv_location_3.setLayoutParams(layoutParams3);
        tv_location_3.setRotation(angle3);

        if(Math.abs(angle1-angle2)<interval_for_stacking){
            //stack required
            float distance1 = currentLocation.distanceTo(targetLocation1);
            float distance2 = currentLocation.distanceTo(targetLocation2);

            if(distance1<distance2){
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_1.setLayoutParams(layoutParams1);
            } else {
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_2.setLayoutParams(layoutParams2);
            }
        }

        if(Math.abs(angle1-angle3)<interval_for_stacking){
            //stack required
            float distance1 = currentLocation.distanceTo(targetLocation1);
            float distance3 = currentLocation.distanceTo(targetLocation3);

            if(distance1<distance3){
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_1.setLayoutParams(layoutParams1);
            } else {
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_3.setLayoutParams(layoutParams3);
            }
        }

        if(Math.abs(angle2-angle3)<interval_for_stacking){
            //stack required
            float distance2 = currentLocation.distanceTo(targetLocation2);
            float distance3 = currentLocation.distanceTo(targetLocation3);

            if(distance2<distance3){
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_2.setLayoutParams(layoutParams2);
            } else {
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_3.setLayoutParams(layoutParams3);
            }
        }

        if(Math.abs(angle2-angle3)<interval_for_stacking && Math.abs(angle1-angle3)<interval_for_stacking){
            //stack required
            float distance2 = currentLocation.distanceTo(targetLocation2);
            float distance3 = currentLocation.distanceTo(targetLocation3);
            float distance1 = currentLocation.distanceTo(targetLocation1);

            if(distance1<distance2 && distance2<distance3){
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_1.setLayoutParams(layoutParams1);
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne);
                tv_location_2.setLayoutParams(layoutParams2);
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne * 1.25);
                tv_location_3.setLayoutParams(layoutParams3);
            } else if(distance1<distance3 && distance3<distance2){
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_1.setLayoutParams(layoutParams1);
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne);
                tv_location_3.setLayoutParams(layoutParams3);
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne * 1.25);
                tv_location_2.setLayoutParams(layoutParams2);
            } else if(distance2<distance1 && distance1<distance3){
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_2.setLayoutParams(layoutParams2);
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne);
                tv_location_1.setLayoutParams(layoutParams1);
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne * 1.25);
                tv_location_3.setLayoutParams(layoutParams3);
            } else if(distance2<distance3 && distance3<distance1){
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_2.setLayoutParams(layoutParams2);
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne);
                tv_location_3.setLayoutParams(layoutParams3);
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne * 1.25);
                tv_location_1.setLayoutParams(layoutParams1);
            } else if(distance3<distance1 && distance1<distance2){
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_3.setLayoutParams(layoutParams3);
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne);
                tv_location_1.setLayoutParams(layoutParams1);
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne * 1.25);
                tv_location_2.setLayoutParams(layoutParams2);
            } else {
                layoutParams3.circleRadius = (int)(circleRadiusLayerOne * 0.75);
                tv_location_3.setLayoutParams(layoutParams3);
                layoutParams2.circleRadius = (int)(circleRadiusLayerOne);
                tv_location_2.setLayoutParams(layoutParams2);
                layoutParams1.circleRadius = (int)(circleRadiusLayerOne * 1.25);
                tv_location_1.setLayoutParams(layoutParams1);
            }

        }



    }


}
