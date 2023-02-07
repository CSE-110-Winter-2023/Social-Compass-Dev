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
    private Location targetLocation;

    private TextView tv_location;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private int circleRadiusLayerOne;

    //private Toast locationError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleRadiusLayerOne = (int) (180 * getResources().getDisplayMetrics().scaledDensity);
        tv_location = (TextView) findViewById(R.id.textView);
        //locationError = Toast.makeText(this, "Please enable location permissions to use Social Compass.", Toast.LENGTH_LONG);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;

                // If a target location has been set, update the angle
                if (targetLocation != null) {
                    updateAngle();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
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
        targetLocation = new Location("Ottawa");
        targetLocation.setLatitude(45.0819);
        targetLocation.setLongitude(-73.3329);
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
        float angle = currentLocation.bearingTo(targetLocation);
        Log.d("tag", String.valueOf(currentLocation.getLatitude()));
        Log.d("tag", String.valueOf(currentLocation.getLongitude()));
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) tv_location.getLayoutParams();
        layoutParams.circleAngle = angle;
        layoutParams.circleRadius = circleRadiusLayerOne;
        tv_location.setLayoutParams(layoutParams);
        tv_location.setRotation(angle);
    }

}
