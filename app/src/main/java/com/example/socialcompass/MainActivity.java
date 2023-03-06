package com.example.socialcompass;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;


/**
 * First activity of the app to be launched. Used to input location to track and will redirect
 * to the compass view when a button is clicked.
 * File contains button click listeners along with methods needed for storing and loading
 * from persistence and a method to validate inputs.
 */
public class MainActivity extends AppCompatActivity {

    //keys stored in values/strings.xml used to store the keys for intents and preferences
    final String parentLabelKey = String.valueOf(R.string.parentLabelKey);
    final String parentLatKey = String.valueOf(R.string.parentLatKey);
    final String parentLongKey = String.valueOf(R.string.parentLongKey);
    final String orientOverrideKey = String.valueOf(R.string.orientOverride);

    /**
     * Called when activity is created, asks for preferences, displays activity_main.xml and loads from preferences
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requesting location permissions
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
        setContentView(R.layout.activity_main);
        loadProfile();
    }

    /**
     * Load location coordinates and label from persistence.
     */
    public void loadProfile(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        float defaultVal = -8888;
        // get location coordinates and label from persistence
        String parentName = preferences.getString(parentLabelKey, "");
        float parentX = preferences.getFloat(parentLatKey, defaultVal);
        float parentY = preferences.getFloat(parentLongKey, defaultVal);
        //parse lat and long as strings for display
        String parentXStr = String.valueOf(parentX);
        String parentYStr = String.valueOf(parentY);
        //if empty turn to empty string so that hint can be shown on UI
        if(parentX == defaultVal){
            parentXStr = "";
        }
        if(parentY == defaultVal){
            parentYStr = "";
        }

        //set text views to loaded variables
        EditText parentLabel = findViewById(R.id.parentLabel);
        parentLabel.setText(parentName);

        EditText parentLat = findViewById(R.id.parentLat);
        parentLat.setText(parentXStr);

        EditText homeLong = findViewById((R.id.parentLong));
        homeLong.setText(parentYStr);
    }

    /**
     * Save a location to persistence
     * @param locationLabel value of label to store
     * @param locationLabelKey key of label to store
     * @param locationLat value of label to store
     * @param locationLatKey key of label to store
     * @param locationLong value of label to store
     * @param locationLongKey key of label to store
     */
    public void saveLocation(String locationLabel, String locationLabelKey, float locationLat, String locationLatKey, float locationLong, String locationLongKey){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(locationLabelKey, locationLabel);
        editor.putFloat(locationLatKey, locationLat);
        editor.putFloat(locationLongKey, locationLong);
        editor.apply();
    }

    private void onLocChange(RemoteLocation location) {
        Log.i("Acer", location.toJSON());


    }


    /**
     * Click listener for the OK button for orientation change.
     * Checks if orientation is valid as well as all other inputs
     * Saves location and starts an intent to compass view activity
     */
    public void onOrientationChangeOkClicked(View view){
        //checking if orientation input is valid
        Log.i("Acer", "ok Clicked");

        var locManager = new LocationManager();
        LiveData<RemoteLocation> loc = locManager.getRemote("publicCode5");

        loc.observe(this, this::onLocChange);




//        EditText orientText = findViewById(R.id.orientInput);
//        boolean orientBoolFilled = !orientText.getText().toString().isEmpty();
//        if(!orientBoolFilled){
//            Utilities.showAlert(this, "Please input new orientation");
//            return;
//        }
//        float orientValue = Float.parseFloat(orientText.getText().toString());
//        if(orientValue < 0 || orientValue > 359){
//            Utilities.showAlert(this, "Please valid new orientation");
//            return;
//        }
//        //converting to radians
//        orientValue = (float) Math.toRadians(orientValue);
//
//        //validating other inputs
//        EditText parentLabel = findViewById(R.id.parentLabel);
//        EditText parentLat = findViewById(R.id.parentLat);
//        EditText parentLong = findViewById(R.id.parentLong);
//        if (!validateLabelInput(parentLabel, parentLat, parentLong)) {
//            return;
//        }
//
//        //saving inputs
//        String homeLabelValue = parentLabel.getText().toString();
//        float homeLatValue = Float.parseFloat(parentLat.getText().toString());
//        float homeLongValue = Float.parseFloat(parentLong.getText().toString());
//        saveLocation(homeLabelValue, parentLabelKey, homeLatValue, parentLatKey, homeLongValue, parentLongKey);
//
//        //starting intent to compass view activity
//        Intent intent = new Intent(this, CompassViewActivity.class);
//        intent.putExtra(parentLabelKey, homeLabelValue);
//        intent.putExtra(parentLatKey, homeLatValue);
//        intent.putExtra(parentLongKey, homeLongValue);
//        intent.putExtra(orientOverrideKey, orientValue);
//        startActivity(intent);
    }

    /**
     * Method used to validate location label and coordinates
     * @param parentLabel is not required
     * @param parentLat required
     * @param parentLong required
     * @return true if valid iputs for loaction
     */
    private boolean validateLabelInput(EditText parentLabel, EditText parentLat, EditText parentLong) {
        //check if there is a location that is not completely  filled
        boolean parentLabelBoolFilled = !parentLabel.getText().toString().isEmpty();
        boolean parentLatBoolFilled = !parentLat.getText().toString().isEmpty();
        boolean parentLongBoolFilled = !parentLong.getText().toString().isEmpty();


        //if one is filled in home they must all be filled
        if(parentLabelBoolFilled || parentLatBoolFilled || parentLongBoolFilled){
            if(!(parentLatBoolFilled && parentLongBoolFilled)){
                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
                return false;
            }
        }
        if(!(parentLabelBoolFilled || parentLatBoolFilled || parentLongBoolFilled)){
            Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
            return false;
        }

        // Check to see if valid input
        if (!Utilities.checkLatitude(Float.parseFloat(parentLat.getText().toString())))
        {
            Utilities.showAlert(this, "Please enter a valid latitude.");
            return false;
        }
        if (!Utilities.checkLongitude(Float.parseFloat(parentLong.getText().toString())))
        {
            Utilities.showAlert(this, "Please enter a valid longitude.");
            return false;
        }

        return true;
    }

    /**
     * Click listener for Submit button if we do not want to overwrite orientation.
     * Checks if inputs are valid then stores into persistence and starts intent.
     */
    public void onSubmitClicked(View view){
        //checks if inputs are valid
        EditText parentLabel = findViewById(R.id.parentLabel);
        EditText parentLat = findViewById(R.id.parentLat);
        EditText parentLong = findViewById(R.id.parentLong);
        if (!validateLabelInput(parentLabel, parentLat, parentLong)) {
            return;
        }

        //store inputs in persistence
        String homeLabelValue = parentLabel.getText().toString();
        float homeLatValue = Float.parseFloat(parentLat.getText().toString());
        float homeLongValue = Float.parseFloat(parentLong.getText().toString());
        saveLocation(homeLabelValue, parentLabelKey, homeLatValue, parentLatKey, homeLongValue, parentLongKey);

        //starts intent to compass view activity
        Intent intent = new Intent(this, CompassViewActivity.class);
        intent.putExtra(parentLabelKey, homeLabelValue);
        intent.putExtra(parentLatKey, homeLatValue);
        intent.putExtra(parentLongKey, homeLongValue);
        startActivity(intent);
    }

}