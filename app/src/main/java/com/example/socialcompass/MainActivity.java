package com.example.socialcompass;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProfile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveProfile();
    }

    public void loadProfile(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        // Coordinates for User's Home
        String homeName = preferences.getString("homeLabel", "");
        String homeX = preferences.getString("homeX", "");
        String homeY = preferences.getString("homeY", "");

        EditText homeLabel = findViewById(R.id.homeLabel);
        homeLabel.setText(homeName);

        EditText homeLat = findViewById(R.id.homeLat);
        homeLat.setText(homeX);

        EditText homeLong = findViewById((R.id.homeLong));
        homeLong.setText(homeY);

        // Coordinates for Family's Home
        String famName = preferences.getString("famLabel:", "");
        String famX = preferences.getString("famX", "");
        String famY = preferences.getString("famY", "");

        EditText famLabel = findViewById(R.id.famLabel);
        famLabel.setText(famName);

        EditText famLat = findViewById(R.id.famLat);
        famLat.setText(famX);

        EditText famLong = findViewById((R.id.famLong));
        famLong.setText(famY);

        // Coordinates for Friend's Home
        String friendName = preferences.getString("friendLabel", "");
        String friendX = preferences.getString("friendX", "");
        String friendY = preferences.getString("friendY", "");

        EditText friendLabel = findViewById(R.id.friendLabel);
        friendLabel.setText(friendName);

        EditText friendLat = findViewById(R.id.friendLat);
        friendLat.setText(friendX);

        EditText friendLong = findViewById((R.id.friendLong));
        friendLong.setText(friendY);
    }
    public void saveProfile(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Persist User's Home Coordinates / Label
        EditText homeLabel = findViewById(R.id.homeLabel);
        editor.putString("homeLabel", homeLabel.getText().toString());

        EditText homeLat = findViewById(R.id.homeLat);

        editor.putString("homeX", homeLat.getText().toString());

        EditText homeLong= findViewById(R.id.homeLong);

        editor.putString("homeY", homeLong.getText().toString());

        // Persist Family's Home Coordinates / Label
        EditText famLabel = findViewById(R.id.famLabel);

        editor.putString("famLabel", famLabel.getText().toString());

        EditText famLat = findViewById(R.id.famLat);


        editor.putString("famX", famLat.getText().toString());

        EditText famLong= findViewById(R.id.famLong);

        editor.putString("famY", famLong.getText().toString());

        // Persist Friend's Home Coordinates / Label
        EditText friendLabel = findViewById(R.id.friendLabel);
        editor.putString("friendLabel", friendLabel.getText().toString());

        EditText friendLat = findViewById(R.id.friendLat);

        editor.putString("friendX", friendLat.getText().toString());

        EditText friendLong= findViewById(R.id.friendLong);

        editor.putString("friendY", friendLong.getText().toString());

        editor.apply();

    }

    //move them out to utilities probs
    private static boolean checkLatitude(float lat){
        if(lat <= -90 || lat > 90){
            return false;
        }
        return true;
    }
    private static boolean checkLongitude(float lon){
        if(lon <= -90 || lon > 90){
            return false;
        }
        return true;
    }

    public void onSubmitClicked(View view){
        //create editTexts for each inputs
        EditText homeLabel = findViewById(R.id.homeLabel);
        EditText homeLat = findViewById(R.id.homeLat);
        EditText homeLong= findViewById(R.id.homeLong);
        EditText famLabel = findViewById(R.id.famLabel);
        EditText famLat = findViewById(R.id.famLat);
        EditText famLong= findViewById(R.id.famLong);
        EditText friendLabel = findViewById(R.id.friendLabel);
        EditText friendLong= findViewById(R.id.friendLong);
        EditText friendLat = findViewById(R.id.friendLat);


        EditText[] latInputs = new EditText[3];
        latInputs[0] = homeLat;
        latInputs[1] = famLat;
        latInputs[2] = friendLat;
        for(EditText latInput : latInputs){
            //check if inputs are empty
            if(!latInput.getText().toString().isEmpty()){
                //get float
                float latInputVal = Float.parseFloat(latInput.getText().toString());
                //if invalid
                if(!checkLatitude(latInputVal)){
                    Utilities.showAlert(this, "Invalid Latitude for Your Home!");
                    return;
                }
            }
        }

        EditText[] lonInputs = new EditText[3];
        lonInputs[0] = homeLong;
        lonInputs[1] = famLong;
        lonInputs[2] = friendLong;
        for(EditText lonInput : lonInputs){
            //check if inputs are empty
            if(!lonInput.getText().toString().isEmpty()){
                //get float
                float longInputVal = Float.parseFloat(lonInput.getText().toString());
                //if invalid
                if(!checkLongitude(longInputVal)){
                    Utilities.showAlert(this, "Invalid Longitude for Your Home!");
                    return;
                }
            }
        }

        //check if there is a location that is not completely  filled
        boolean homeLabelBoolFilled = !homeLabel.getText().toString().isEmpty();
        boolean homeLatBoolFilled = !homeLat.getText().toString().isEmpty();
        boolean homeLongBoolFilled = !homeLong.getText().toString().isEmpty();
        boolean famLabelBoolFilled = !famLabel.getText().toString().isEmpty();
        boolean famLatBoolFilled = !famLat.getText().toString().isEmpty();
        boolean famLongBoolFilled = !famLong.getText().toString().isEmpty();
        boolean friendLabelBoolFilled = !friendLabel.getText().toString().isEmpty();
        boolean friendLatBoolFilled = !friendLat.getText().toString().isEmpty();
        boolean friendLongBoolFilled = !friendLong.getText().toString().isEmpty();

        //if one is filled in home they must all be filled
        if(homeLabelBoolFilled || homeLatBoolFilled || homeLongBoolFilled){
            if(!(homeLatBoolFilled && homeLongBoolFilled)){
                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
                return;
            }
        }

        //if one is filled in home they must all be filled
        if(famLabelBoolFilled || famLatBoolFilled || famLongBoolFilled){
            if(!(famLatBoolFilled && famLongBoolFilled)){
                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
                return;
            }
        }

        //if one is filled in home they must all be filled
        if(friendLabelBoolFilled || friendLatBoolFilled || friendLongBoolFilled){
            if(!(friendLatBoolFilled && friendLongBoolFilled)){
                Utilities.showAlert(this, "Please do not leave unfilled fields for a location");
                return;
            }
        }

        //later instead of calling finish we will call saveProfile and intent to compassActivity
        finish();
    }
}
