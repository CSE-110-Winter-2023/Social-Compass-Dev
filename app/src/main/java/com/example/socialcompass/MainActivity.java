package com.example.socialcompass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean hasHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void checkUser() {
        if (preferences != null) {
            boolean hasLocation = preferences.getBoolean("hasLocation", false);
            if (hasLocation) {
                hasHome = true;
            } else {
                hasHome = false;
            }
        }
    }

    public void saveLocation(String label, String labelKey, float lat, String latKey, float lng, String lngKey) {
        if (editor == null) {
            preferences = getPreferences(MODE_PRIVATE);
            editor = preferences.edit();
        }
        editor.putString(labelKey, label);
        editor.putFloat(latKey, lat);
        editor.putFloat(lngKey, lng);
        editor.putBoolean("hasLocation", true);
        editor.apply();
    }

    public boolean hasHome() {
        return hasHome;
    }
}





//package com.example.socialcompass;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//
//    final String homeLabelKey = "homeLabelKey";
//    final String homeLatKey = "homeLatKey";
//    final String homeLongKey = "homeLongKey";
//    final String famLabelKey = "famLabelKey";
//    final String famLatKey = "famLatKey";
//    final String famLongKey = "famLongKey";
//    final String friendLabelKey = "friendLabelKey";
//    final String friendLatKey = "friendLatKey";
//    final String friendLongKey = "friendLongKey";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        loadProfile();
//    }
//
//    public void loadProfile(){
//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//
//        // Coordinates for User's Home
//        String homeName = preferences.getString(homeLabelKey, "");
//        String homeX = preferences.getString(homeLatKey, "");
//        String homeY = preferences.getString(homeLongKey, "");
//
//        EditText homeLabel = findViewById(R.id.homeLabel);
//        homeLabel.setText(homeName);
//
//        EditText homeLat = findViewById(R.id.homeLat);
//        homeLat.setText(homeX);
//
//        EditText homeLong = findViewById((R.id.homeLong));
//        homeLong.setText(homeY);
//
//        // Coordinates for Family's Home
//        String famName = preferences.getString(famLabelKey, "");
//        String famX = preferences.getString(famLatKey, "");
//        String famY = preferences.getString(famLongKey, "");
//
////        EditText famLabel = findViewById(R.id.famLabel);
////        famLabel.setText(famName);
////
////        EditText famLat = findViewById(R.id.famLat);
////        famLat.setText(famX);
////
////        EditText famLong = findViewById((R.id.famLong));
////        famLong.setText(famY);
//
//        // Coordinates for Friend's Home
////        String friendName = preferences.getString(friendLabelKey, "");
////        String friendX = preferences.getString(friendLatKey, "");
////        String friendY = preferences.getString(friendLongKey, "");
////
////        EditText friendLabel = findViewById(R.id.friendLabel);
////        friendLabel.setText(friendName);
////
////        EditText friendLat = findViewById(R.id.friendLat);
////        friendLat.setText(friendX);
////
////        EditText friendLong = findViewById((R.id.friendLong));
////        friendLong.setText(friendY);
//    }
//
//    //move them out to utilities probs
//    private static boolean checkLatitude(float lat){
//        if(lat <= -90 || lat >= 90){
//            return false;
//        }
//        return true;
//    }
//    private static boolean checkLongitude(float lon){
//        if(lon <= -180 || lon > 180){
//            return false;
//        }
//        return true;
//    }
//
//    public void saveLocation(String locationLabel, String locationLabelKey, float locationLat, String locationLatKey, float locationLong, String locationLongKey){
//        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        String locationLatString = "" + locationLat;
//        String locationLongString = "" + locationLong;
//
//
//        editor.putString(locationLabelKey, locationLabel);
////        editor.putFloat(locationLatKey, locationLat);
////        editor.putFloat(locationLongKey, locationLong);
//        editor.putString(locationLatKey, locationLatString);
//        editor.putString(locationLongKey, locationLongString);
//        editor.apply();
//    }
//
//    public void onSubmitClicked(View view){
//        //create editTexts for each inputs
//        EditText homeLabel = findViewById(R.id.homeLabel);
//        EditText homeLat = findViewById(R.id.homeLat);
//        EditText homeLong= findViewById(R.id.homeLong);
////        EditText famLabel = findViewById(R.id.famLabel);
////        EditText famLat = findViewById(R.id.famLat);
////        EditText famLong= findViewById(R.id.famLong);
////        EditText friendLabel = findViewById(R.id.friendLabel);
////        EditText friendLong= findViewById(R.id.friendLong);
////        EditText friendLat = findViewById(R.id.friendLat);
//
//
////        EditText[] latInputs = new EditText[3];
////        latInputs[0] = homeLat;
////        latInputs[1] = famLat;
////        latInputs[2] = friendLat;
//
////        for(EditText latInput : latInputs){
////            //check if inputs are empty
////            if(!latInput.getText().toString().isEmpty()){
////                //get float
////                float latInputVal = Float.parseFloat(latInput.getText().toString());
////                //if invalid
////                if(!checkLatitude(latInputVal)){
////                    Utilities.showAlert(this, "Invalid Latitude for Your Home!");
////                    return;
////                }
////            }
////        }
//
//        EditText[] lonInputs = new EditText[3];
//        lonInputs[0] = homeLong;
////        lonInputs[1] = famLong;
////        lonInputs[2] = friendLong;
////        for(EditText lonInput : lonInputs){
////            //check if inputs are empty
////            if(!lonInput.getText().toString().isEmpty()){
////                //get float
////                float longInputVal = Float.parseFloat(lonInput.getText().toString());
////                //if invalid
////                if(!checkLongitude(longInputVal)){
////                    Utilities.showAlert(this, "Invalid Longitude for Your Home!");
////                    return;
////                }
////            }
////        }
//
//        //check if there is a location that is not completely  filled
//        boolean homeLabelBoolFilled = !homeLabel.getText().toString().isEmpty();
//        boolean homeLatBoolFilled = !homeLat.getText().toString().isEmpty();
//        boolean homeLongBoolFilled = !homeLong.getText().toString().isEmpty();
////        boolean famLabelBoolFilled = !famLabel.getText().toString().isEmpty();
////        boolean famLatBoolFilled = !famLat.getText().toString().isEmpty();
////        boolean famLongBoolFilled = !famLong.getText().toString().isEmpty();
////        boolean friendLabelBoolFilled = !friendLabel.getText().toString().isEmpty();
////        boolean friendLatBoolFilled = !friendLat.getText().toString().isEmpty();
////        boolean friendLongBoolFilled = !friendLong.getText().toString().isEmpty();
//
//        //if one is filled in home they must all be filled
//        if(homeLabelBoolFilled || homeLatBoolFilled || homeLongBoolFilled){
//            if(!(homeLatBoolFilled && homeLongBoolFilled)){
//                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
//                return;
//            }
//            String homeLabelValue = homeLabel.getText().toString();
//            float homeLatValue = Float.parseFloat(homeLat.getText().toString());
//            float homeLongValue = Float.parseFloat(homeLong.getText().toString());
//            saveLocation(homeLabelValue, homeLabelKey, homeLatValue, homeLatKey, homeLongValue, homeLongKey);
//        }
//
//        //if one is filled in fam they must all be filled
////        if(famLabelBoolFilled || famLatBoolFilled || famLongBoolFilled){
////            if(!(famLatBoolFilled && famLongBoolFilled)){
////                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
////                return;
////            }
////            String famLabelValue = famLabel.getText().toString();
////            float famLatValue = Float.parseFloat(famLat.getText().toString());
////            float famLongValue = Float.parseFloat(famLong.getText().toString());
////            saveLocation(famLabelValue, famLabelKey, famLatValue, famLatKey, famLongValue, famLongKey);
////        }
//
//        //if one is filled in friend they must all be filled
////        if(friendLabelBoolFilled || friendLatBoolFilled || friendLongBoolFilled){
////            if(!(friendLatBoolFilled && friendLongBoolFilled)){
////                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
////                return;
////            }
////            String friendLabelValue = friendLabel.getText().toString();
////            float friendLatValue = Float.parseFloat(friendLat.getText().toString());
////            float friendLongValue = Float.parseFloat(friendLong.getText().toString());
////            saveLocation(friendLabelValue, friendLabelKey, friendLatValue, friendLatKey, friendLongValue, friendLongKey);
////        }
//
//        if(!(homeLabelBoolFilled&&homeLatBoolFilled&&homeLongBoolFilled)){
//            saveLocation("", homeLabelKey, 0, homeLatKey, 0, homeLongKey);
//        }
////        if(!(famLabelBoolFilled&&famLatBoolFilled&&famLongBoolFilled)){
////            saveLocation("", famLabelKey, 0, famLatKey, 0, famLongKey);
////        }
////        if(!(friendLabelBoolFilled&&friendLatBoolFilled&&friendLongBoolFilled)){
////            saveLocation("", friendLabelKey, 0, friendLatKey, 0, friendLongKey);
////        }
//
//        //last check if everything is empty
//        if(!(homeLabelBoolFilled || homeLatBoolFilled || homeLongBoolFilled)){
//            Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
//            return;
//        }
//
//        //later instead of calling finish we will call saveProfile and intent to compassActivity
//        finish();
//
//    }
//}

