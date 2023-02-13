package com.example.socialcompass;

import static com.example.socialcompass.MainActivity.updateUIMain;

import android.widget.TextView;

public class CompassController {
    private float locAngle;
    private float orientAngle;
    private int distance;

    public CompassController(float locAngle, float orientAngle, int distance){
        this.locAngle = locAngle;
        this.orientAngle = orientAngle;
        this.distance = distance;
    }

    public void setLocAngle(float loc){
        locAngle = loc;
    }

    public float getLocAngle(){
        return this.locAngle;
    }

    public void setOrient(float orientation){
        orientAngle = orientation;
    }

    public float getOrient(){
        return this.orientAngle;
    }

    public void updateUI(TextView tv){
        float angle1 = locAngle + orientAngle;
        updateUIMain(angle1, tv, distance);
    }
}
