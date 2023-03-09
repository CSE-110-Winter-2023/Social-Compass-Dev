package com.example.socialcompass;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executors;

public class CompassViewActivity extends AppCompatActivity {
    private OrientationService orientationService;
    private LocationService locationService;
    Location currentLocation;
    private CompassLocationContainer locations;
    private int circleRadiusLayerOne;
    final String userPrivateKey = "team4PrivateKey";
    final String userPublicKey = "team4PublicKey";
    private FriendViewModel viewModel;
    String ourDisplayName = LocationAPI.provide().getFromRemoteAPIAsync(userPublicKey).label;


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

        viewModel = new ViewModelProvider(this).get(FriendViewModel.class);

        currentLocation = new Location("User Location");
        currentLocation.setLatitude(90.0000);
        currentLocation.setLongitude(90.0000);

        locations = CompassLocationContainer.singleton();

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }

        circleRadiusLayerOne = (int) (180 * getResources().getDisplayMetrics().scaledDensity);

        for (userID uuid : viewModel.getFriendsSync()) {
            Log.i("[HERE]", uuid.friendID);
            AddLocationToActivity(uuid.friendID);
        }

        locationService = LocationService.singleton(this);
        orientationService = OrientationService.singleton(this);

        locationService.getLocation().observe(this, loc -> {
            currentLocation.setLatitude(loc.first);
            currentLocation.setLongitude(loc.second);

            LocationAPI.provide().upsertToRemoteAPIAsync(userPrivateKey, userPublicKey, currentLocation, this.ourDisplayName);

            for (CompassLocationObject o_loc : locations){
                float angle = currentLocation.bearingTo(o_loc.getLocation());

                var trueDistance = currentLocation.distanceTo(o_loc.getLocation());
                var relativeDistance = (int) ((trueDistance/6378153) * (circleRadiusLayerOne/3));
//                System.out.println(circleRadiusLayerOne);
//                System.out.println(relativeDistance);

                o_loc.getController().setDistance(relativeDistance);
                o_loc.getController().setLocAngle(angle);
                o_loc.getController().updateUI();
            }
        });

        orientationService.getOrientation().observe(this, orientation -> {
            float piFloat = (float) Math.PI;
            float angle = -1 * orientation * 180 / piFloat;

            for (CompassLocationObject o_loc : locations) {
                o_loc.getController().setOrient(angle);
                o_loc.getController().updateUI();
            }
        });
        orientationService.registerSensorListener();
    }

    public void onBackClicked(View view){
        finish();
    }

    private void AddLocationToActivity(String publickey) {
        CompassUIController ui_controller = new CompassUIController(0,0, circleRadiusLayerOne, null);
        TextView cur_text_view = Utilities.createCompassLocationTextView(this, publickey, 0, 0, 20f, false);
        ((ConstraintLayout) findViewById(R.id.clock)).addView(cur_text_view);
        ui_controller.setTextView(cur_text_view);
        locations.createAndAddLocation(publickey, ui_controller);
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
    public void onSettingsClicked(View view) {
        //starts intent to preferences view activity
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivityForResult(intent, PreferencesActivity.REQUEST_CODE);
    }


    public void setDisplayName(String name){
        this.ourDisplayName = name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String fetchedDisplayName = data.getStringExtra("newDisplayName");
        if(!fetchedDisplayName.equals("error")){
            setDisplayName(fetchedDisplayName);
        }



        if (requestCode == PreferencesActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            // Called when finished from PreferenceActivity and a name was added

//            String fetchedDisplayName = data.getStringExtra("newDisplayName");
//            setDisplayName(fetchedDisplayName);
//            if(!fetchedDisplayName.equals("error")){
//                setDisplayName("joe");
//            }
//            Log.i("Debuug", "YOOOOOOOOO" + fetchedDisplayName);

            locations.clear();

            for (userID uuid : viewModel.getFriendsSync()) {
                Log.i("[ONFINISH]", uuid.friendID);
                AddLocationToActivity(uuid.friendID);
            }
        }
    }
}