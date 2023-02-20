package com.example.socialcompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    final String parentLabelKey = String.valueOf(R.string.parentLabelKey);
    final String parentLatKey = String.valueOf(R.string.parentLatKey);
    final String parentLongKey = String.valueOf(R.string.parentLongKey);
    final String orientOverrideKey = String.valueOf(R.string.orientOverride);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }

        setContentView(R.layout.activity_main);
        loadProfile();
    }

    public void loadProfile(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        float defaultVal = -8888;
        // Coordinates for Parent's Home
        String parentName = preferences.getString(parentLabelKey, "");
        float parentX = preferences.getFloat(parentLatKey, defaultVal);
        float parentY = preferences.getFloat(parentLongKey, defaultVal);
        String parentXStr = String.valueOf(parentX);
        String parentYStr = String.valueOf(parentY);
        if(parentX == defaultVal){
            parentXStr = "";
        }
        if(parentY == defaultVal){
            parentYStr = "";
        }

        EditText parentLabel = findViewById(R.id.parentLabel);
        parentLabel.setText(parentName);

        EditText parentLat = findViewById(R.id.parentLat);
        parentLat.setText(parentXStr);

        EditText homeLong = findViewById((R.id.parentLong));
        homeLong.setText(parentYStr);
    }

    public void saveLocation(String locationLabel, String locationLabelKey, float locationLat, String locationLatKey, float locationLong, String locationLongKey){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(locationLabelKey, locationLabel);
        editor.putFloat(locationLatKey, locationLat);
        editor.putFloat(locationLongKey, locationLong);
        editor.apply();
    }

    public void onOrientationChangeOkClicked(View view){
        EditText orientText = findViewById(R.id.orientInput);
        boolean orientBoolFilled = !orientText.getText().toString().isEmpty();
        if(!orientBoolFilled){
            Utilities.showAlert(this, "Please input new orientation");
            return;
        }
        float orientValue = Float.parseFloat(orientText.getText().toString());
        if(orientValue < 0 || orientValue > 359){
            Utilities.showAlert(this, "Please valid new orientation");
            return;
        }

        orientValue = (float) Math.toRadians(orientValue);

        EditText parentLabel = findViewById(R.id.parentLabel);
        EditText parentLat = findViewById(R.id.parentLat);
        EditText parentLong = findViewById(R.id.parentLong);
        if (!validateLabelInput(parentLabel, parentLat, parentLong)) {
            return;
        }

        String homeLabelValue = parentLabel.getText().toString();
        float homeLatValue = Float.parseFloat(parentLat.getText().toString());
        float homeLongValue = Float.parseFloat(parentLong.getText().toString());
        saveLocation(homeLabelValue, parentLabelKey, homeLatValue, parentLatKey, homeLongValue, parentLongKey);


        Intent intent = new Intent(this, CompassViewActivity.class);
        intent.putExtra(parentLabelKey, homeLabelValue);
        intent.putExtra(parentLatKey, homeLatValue);
        intent.putExtra(parentLongKey, homeLongValue);
        intent.putExtra(orientOverrideKey, orientValue);
        startActivity(intent);
    }

    private boolean validateLabelInput(EditText parentLabel, EditText parentLat, EditText parentLong) {
        //check if there is a location that is not completely  filled
        boolean parentLabelBoolFilled = !parentLabel.getText().toString().isEmpty();
        boolean parentLatBoolFilled = !parentLat.getText().toString().isEmpty();
        boolean parentLongBoolFilled = !parentLong.getText().toString().isEmpty();
        System.out.println(parentLabelBoolFilled);
        System.out.println(parentLatBoolFilled);
        System.out.println(parentLongBoolFilled);
        //if one is filled in home they must all be filled
        if(parentLabelBoolFilled || parentLatBoolFilled || parentLongBoolFilled){
            if(!(parentLatBoolFilled && parentLongBoolFilled)){
                System.out.println("TOP ONE");
                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
                return false;
            }
        }

        if(!(parentLabelBoolFilled || parentLatBoolFilled || parentLongBoolFilled)){
            System.out.println("BOTTOM ONE");
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
    public void onSubmitClicked(View view){
        //create editTexts for each inputs
        EditText parentLabel = findViewById(R.id.parentLabel);
        EditText parentLat = findViewById(R.id.parentLat);
        EditText parentLong = findViewById(R.id.parentLong);

        if (!validateLabelInput(parentLabel, parentLat, parentLong)) {
            return;
        }

        // If all validation checks have passed
        String homeLabelValue = parentLabel.getText().toString();
        float homeLatValue = Float.parseFloat(parentLat.getText().toString());
        float homeLongValue = Float.parseFloat(parentLong.getText().toString());
        saveLocation(homeLabelValue, parentLabelKey, homeLatValue, parentLatKey, homeLongValue, parentLongKey);

        Intent intent = new Intent(this, CompassViewActivity.class);
        intent.putExtra(parentLabelKey, homeLabelValue);
        intent.putExtra(parentLatKey, homeLatValue);
        intent.putExtra(parentLongKey, homeLongValue);


//        EditText orientText = findViewById(R.id.orientInput);
//        boolean orientBoolFilled = !orientText.getText().toString().isEmpty();
//        if(!orientBoolFilled){
//            startActivity(intent);
//            return;
//        }
//        float orientValue = Float.parseFloat(orientText.getText().toString());
//        if(orientValue < 0 || orientValue > 359){
//            Utilities.showAlert(this, "Please valid new orientation");
//            return;
//        }
//
//        orientValue = (float) Math.toRadians(orientValue);
//        intent.putExtra(orientOverrideKey, orientValue);
        startActivity(intent);
    }

}