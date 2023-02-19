package com.example.socialcompass;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

public class CompassViewActivity extends AppCompatActivity {
    private OrientationService orientationService;
    private LocationService locationService;
    Location currentLocation;
    private CompassLocationContainer locations;
    private int circleRadiusLayerOne;


    final String parentLabelKey = String.valueOf(R.string.parentLabelKey);
    final String parentLatKey = String.valueOf(R.string.parentLatKey);
    final String parentLongKey = String.valueOf(R.string.parentLongKey);
    final String orientOverrideKey = String.valueOf(R.string.orientOverride);
    final String orientOverrideBoolKey = String.valueOf(R.string.orientOverrideBool);

//    @Override
//    protected void onPause(){
//        super.onPause();
//        orientationService.unregisterSensorListeners();
//        locationService.unregisterLocationListener();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass_view);

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

        float defaultVal = -8888;
        Intent intent = getIntent();
        String parentLabelValue = intent.getStringExtra(parentLabelKey);
        float parentLatValue = intent.getFloatExtra(parentLatKey, defaultVal);
        float parentLongValue = intent.getFloatExtra(parentLongKey, defaultVal);
        if(parentLatValue == defaultVal){
            Utilities.showAlert(this, "Error Occurred: Lat Value is " + parentLatValue);
            return;
        }
        if(parentLongValue == defaultVal){
            Utilities.showAlert(this, "Error Occurred: Long Value is " + parentLongValue);
            return;
        }

        boolean overwriteOr = intent.getBooleanExtra(orientOverrideBoolKey, false);
        System.out.println(overwriteOr);
        System.out.println("tracking " + parentLabelValue + " at lat " + parentLatValue + " long " + parentLongValue);
        if(overwriteOr){
            float newOr = intent.getFloatExtra(orientOverrideKey, defaultVal);
            if(newOr == defaultVal){
                Utilities.showAlert(this, "bad orientation problem");
                return;
            }

            String[] names = new String[]{parentLabelValue};
            float[] lat = new float[]{parentLatValue};
            float[] lon = new float[]{parentLongValue};

            for (int i = 0; i < names.length; i++) {
                AddLocationToActivity(names[i], lat[i], lon[i]);
            }

            locationService = LocationService.singleton(this);
            orientationService = OrientationService.singleton(this);

            locationService.getLocation().observe(this, loc -> {
                currentLocation.setLatitude(loc.first);
                currentLocation.setLongitude(loc.second);

                for (CompassLocationObject o_loc : locations){
                    float angle = currentLocation.bearingTo(o_loc.getLocation());
                    o_loc.getController().setLocAngle(angle);
                    o_loc.getController().updateUI();
                }
            });
            MutableLiveData<Float> mockDataSource = new MutableLiveData<>();
            orientationService.setMockOrientationSource(mockDataSource);
            mockDataSource.setValue(newOr);

        } else {
            String[] names = new String[]{parentLabelValue};
            float[] lat = new float[]{parentLatValue};
            float[] lon = new float[]{parentLongValue};

            for (int i = 0; i < names.length; i++) {
                AddLocationToActivity(names[i], lat[i], lon[i]);
            }

            locationService = LocationService.singleton(this);
            orientationService = OrientationService.singleton(this);

            locationService.getLocation().observe(this, loc -> {
                currentLocation.setLatitude(loc.first);
                currentLocation.setLongitude(loc.second);

                for (CompassLocationObject o_loc : locations){
                    float angle = currentLocation.bearingTo(o_loc.getLocation());
                    o_loc.getController().setLocAngle(angle);
                    o_loc.getController().updateUI();
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
    }

    public void onBackClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void AddLocationToActivity(String name, float lat, float lon) {
        CompassUIController ui_controller = new CompassUIController(0,0, circleRadiusLayerOne, null);
        TextView cur_text_view = Utilities.createCompassLocationTextView(this, name, 0, 0, 20f, true);
        ((ConstraintLayout) findViewById(R.id.clock)).addView(cur_text_view);
        ui_controller.setTextView(cur_text_view);
        locations.createAndAddLocation(name, lat, lon, ui_controller);
    }

    public CompassLocationContainer getLocationContainer() {
        return locations;
    }

    public LocationService getLocationService() {
        return locationService;
    }

    public OrientationService getOrientationService() {
        return orientationService;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    locations.syncAllNames();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}