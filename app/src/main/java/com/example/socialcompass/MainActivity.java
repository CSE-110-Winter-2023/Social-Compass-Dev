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

    public void onSubmitClicked(View view){
        //Checks if user's home coordinates are valid
        EditText homeLat = findViewById(R.id.homeLat);
        EditText homeLong= findViewById(R.id.homeLong);
        if(!homeLat.getText().toString().isEmpty() && !homeLong.getText().toString().isEmpty()) {
            if (Double.valueOf(homeLat.getText().toString()) <= -90 || Double.valueOf(homeLat.getText().toString()) > 90) {
                Utilities.showAlert(this, "Invalid Latitude for Your Home!");
                return;
            }
            if (Double.valueOf(homeLong.getText().toString()) <= -180 || Double.valueOf(homeLong.getText().toString()) > 180) {
                Utilities.showAlert(this, "Invalid Longitude for Your Home!");
                return;
            }
        }


        //Check if Family Coordinate are valid
        EditText famLat = findViewById(R.id.famLat);
        EditText famLong= findViewById(R.id.famLong);
        if(!famLat.getText().toString().isEmpty() && !famLong.getText().toString().isEmpty()) {
            if (Double.valueOf(famLat.getText().toString()) <= -90 || Double.valueOf(famLat.getText().toString()) > 90) {
                Utilities.showAlert(this, "Invalid Latitude for Family's Home!");
                return;
            }
            if (Double.valueOf(famLong.getText().toString()) <= -180 || Double.valueOf(famLong.getText().toString()) > 180) {
                Utilities.showAlert(this, "Invalid Longitude for Family's Home!");
                return;
            }
        }


        //Check if Friend Coordinates are valid
        EditText friendLong= findViewById(R.id.friendLong);
        EditText friendLat = findViewById(R.id.friendLat);
        if(!friendLat.getText().toString().isEmpty() && !friendLong.getText().toString().isEmpty()) {
            if (Double.valueOf(friendLat.getText().toString()) <= -90 || Double.valueOf(friendLat.getText().toString()) > 90) {
                Utilities.showAlert(this, "Invalid Latitude for Friend's Home!");
                return;
            }
            if (Double.valueOf(friendLong.getText().toString()) <= -180 || Double.valueOf(friendLong.getText().toString()) > 180) {
                Utilities.showAlert(this, "Invalid Longitude for Friend's Home!");
                return;
            }
        }

        finish();
    }
}
