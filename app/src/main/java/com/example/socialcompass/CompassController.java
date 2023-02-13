package com.example.socialcompass;

import static com.example.socialcompass.MainActivity.updateUIMain;

import android.widget.TextView;

public class CompassController {
    private float locAngle;
    private float orientAngle;
    private TextView tv;
    private int distance;

    public CompassController(float locAngle, float orientAngle, TextView tv, int distance){
        this.locAngle = locAngle;
        this.orientAngle = orientAngle;
        this.tv = tv;
        this.distance = distance;
    }

    public void setLocAngle(float loc){
        locAngle = loc;
        updateUI();
    }

    public void setOrient(float orientation){
        orientAngle = orientation;
        updateUI();
    }


    public void updateUI(){
        float angle1 = locAngle + orientAngle;
        updateUIMain(angle1, tv, distance);
    }
}
