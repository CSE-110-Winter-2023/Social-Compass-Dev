package com.example.socialcompass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
    }


    public void onSubmitClicked(View view) {
        //starts intent to compass view activity
        Intent intent = new Intent(this, CompassViewActivity.class);
        startActivity(intent);
    }
}