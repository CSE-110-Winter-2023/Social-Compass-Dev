package com.example.socialcompass;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private OrientationService orientationService;
    private LocationService locationService;

    Location currentLocation;

    private CompassLocationContainer locations;

    private int circleRadiusLayerOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLocation = new Location("User Location");
        currentLocation.setLatitude(90.0000);
        currentLocation.setLongitude(90.0000);

        locations = new CompassLocationContainer();

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
        circleRadiusLayerOne = (int) (180 * getResources().getDisplayMetrics().scaledDensity);

        CompassUIController cp1 = new CompassUIController(0,0, circleRadiusLayerOne, (TextView) findViewById(R.id.home1));
        CompassUIController cp2 = new CompassUIController(0,0, circleRadiusLayerOne, (TextView) findViewById(R.id.home2));
        CompassUIController cp3 = new CompassUIController(0,0, circleRadiusLayerOne, (TextView) findViewById(R.id.home3));

        locations.createAndAddLocation("Home", 45.0819, 73.3329, cp1);
        locations.createAndAddLocation("Parents", 20.0819, -72.3329, cp2);
        locations.createAndAddLocation("Friend", 30.0819, -50.3329, cp3);

        locationService = LocationService.singleton(this);
        orientationService = new OrientationService(this);


        locationService.getLocation().observe(this, loc-> {
            currentLocation.setLatitude(loc.first);
            currentLocation.setLongitude(loc.second);

            for (CompassLocationObject o_loc : locations){
                float angle = currentLocation.bearingTo(o_loc.getLocation());
                o_loc.getController().setLocAngle(angle);
                o_loc.getController().updateUI();

                Log.d("L_OBSERVER", Float.toString(((TextView) findViewById(R.id.home1)).getX()));
            }
        });

        orientationService.getOrientation().observe(this, orientation -> {
            float piFloat = (float) Math.PI;
            float angle = -1*orientation*180/piFloat;

            for (CompassLocationObject o_loc : locations) {
                o_loc.getController().setOrient(angle);
                o_loc.getController().updateUI();
            }
        });
    }

    public static void updateUIMain(float angle, TextView tv, int distance){
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) tv.getLayoutParams();
        layoutParams1.circleAngle = angle;
        layoutParams1.circleRadius = distance;
        tv.setLayoutParams(layoutParams1);
        tv.setRotation(angle);
    }

    public CompassLocationContainer getLocationContainer() {
        return locations;
    }
}