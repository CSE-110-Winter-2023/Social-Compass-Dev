/**
 * The CompassUIController class is responsible for updating the UI for the compass. It takes
 * the current device location and orientation angles, as well as the distance to the target
 * location, and updates the position and rotation of the compass needle accordingly. It
 * implements the UIController interface to provide a common way to update the UI for different
 * types of views.
 */

package com.example.socialcompass;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CompassUIController implements UIController {
    private float locAngle;
    private float orientAngle;
    private float distance;
    private TextView tv;
    private View imageViewDot;

    /**
     * Constructor for the CompassUIController class. It initializes the instance variables for the
     * location angle, orientation angle, distance, and the TextView used to display the compass
     * needle.
     *
     * @param locAngle The location angle in degrees
     * @param orientAngle The orientation angle in degrees
     * @param distance The distance to the target location
     * @param tv The TextView used to display the compass needle
     */
    public CompassUIController(float locAngle, float orientAngle, float distance, TextView tv, View imageViewDot){
        this.locAngle = locAngle;
        this.orientAngle = orientAngle;
        this.distance = distance;
        this.tv = tv;
        this.imageViewDot = imageViewDot;
    }

    /**
     * Sets the location angle to the given value.
     *
     * @param loc The new location angle in degrees
     */
    public void setLocAngle(float loc){
        locAngle = loc;
    }

    /**
     * Returns the current location angle.
     *
     * @return The current location angle in degrees
     */
    public float getLocAngle(){
        return this.locAngle;
    }

    /**
     * Sets the orientation angle to the given value.
     *
     * @param orientation The new orientation angle in degrees
     */
    public void setOrient(float orientation){
        orientAngle = orientation;
    }


    /**
     * Returns the current orientation angle.
     *
     * @return The current orientation angle in degrees
     */
    public float getOrient(){
        return this.orientAngle;
    }

    /**
     * Sets the TextView used to display the compass needle to the given TextView.
     *
     * @param tv The new TextView used to display the compass needle
     */
    public void setTextView(TextView tv) {
        this.tv = tv;
    }

    public void setDotTextView(View imageViewDot) {
        this.imageViewDot = imageViewDot;
    }

    /**
     * Returns the current TextView used to display the compass needle.
     *
     * @return The current TextView used to display the compass needle
     */
    public TextView getTextView() {
        return tv;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistance() {
        return this.distance;
    }
    /**
     * Updates the UI for the compass. It calculates the new angle for the compass needle, based on
     * the location and orientation angles, and updates the position and rotation of the TextView
     * used to display the compass needle accordingly.
     */
    @Override
    public void updateUI(){
        float angle = locAngle + orientAngle;

        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) tv.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParamsDot = (ConstraintLayout.LayoutParams) imageViewDot.getLayoutParams();

        layoutParamsDot.circleAngle = angle;
        layoutParamsDot.circleRadius = 605;

        layoutParams1.circleAngle = angle;
        layoutParams1.circleRadius = ZoomLevel.singleton(null).calculateDistance(this.distance);

        if (ZoomLevel.singleton(null).distanceInView(this.distance)) {
            System.out.println("VISIBLE");
            tv.setVisibility(View.VISIBLE);
            imageViewDot.setVisibility(View.INVISIBLE);
        } else {
            System.out.println("INVISIBLE");
            tv.setVisibility(View.INVISIBLE);
            imageViewDot.setVisibility(View.VISIBLE);
        }

        imageViewDot.setLayoutParams(layoutParamsDot);
        tv.setLayoutParams(layoutParams1);

        tv.setRotation(0);
    }
}
