package com.example.socialcompass;

import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CompassUIController implements UIController {
    private float locAngle;
    private float orientAngle;
    private int distance;
    private TextView tv;

    public CompassUIController(float locAngle, float orientAngle, int distance, TextView tv){
        this.locAngle = locAngle;
        this.orientAngle = orientAngle;
        this.distance = distance;
        this.tv = tv;
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

    public void setTextView(TextView tv) {
        this.tv = tv;
    }

    public TextView getTextView() {
        return tv;
    }

    @Override
    public void updateUI(){
        float angle = locAngle + orientAngle;
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) tv.getLayoutParams();
        layoutParams1.circleAngle = angle;
        layoutParams1.circleRadius = distance;
        tv.setLayoutParams(layoutParams1);
        tv.setRotation(angle);
    }
}
