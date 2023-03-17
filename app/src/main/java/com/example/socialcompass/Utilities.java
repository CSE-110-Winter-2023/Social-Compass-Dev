/*
 * This class provides utility functions that are used throughout the app,
 * such as creating text views, checking the validity of latitude and longitude values,
 * and showing alert dialogs.
 */

package com.example.socialcompass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.UUID;

public class Utilities {

    public static String createUUID()
    {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }

    /**
     * Shows an alert dialog with the specified message.
     *
     * @param activity The activity to which the alert dialog is bound.
     * @param message The message to be displayed in the alert dialog.
     * @return The alert dialog object that is displayed.
     */
    public static AlertDialog  showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, id)-> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();

        return alertDialog;
    }

    /**
     * Checks if the given latitude is a valid value.
     *
     * @param lat The latitude value to check.
     * @return True if the latitude is valid, false otherwise.
     */
    public static boolean checkLatitude(float lat){
        if(lat < -90 || lat >= 90) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the given longitude is a valid value.
     *
     * @param Long The longitude value to check.
     * @return True if the longitude is valid, false otherwise.
     */
    public static boolean checkLongitude(float Long){
        if(Long < -180 || Long >= 180){
            return false;
        }
        return true;
    }

    /**
     * Creates and returns a text view that is used to display a compass location.
     *
     * @param context The context of the activity in which the text view will be created.
     * @param displayText The text to be displayed in the text view.
     * @param angle The angle at which the text view will be rotated.
     * @param radius The radius of the circle in which the text view will be placed.
     * @param textSize The size of the text in the text view.
     * @param editable A flag indicating whether the text view should be editable or not.
     * @return The text view that was created.
     */
    public static TextView createCompassLocationTextView(Context context, String displayText, float angle, int radius, float textSize, boolean editable) {
        TextView textView;
        if (editable) {
            textView = new EditText(context);
            textView.setHint("Location");
            textView.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            textView.setBackground(null);
            textView.setSingleLine(true);
            textView.setCursorVisible(false);
            textView.setFocusableInTouchMode(true);
            textView.setLongClickable(false);

            textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    textView.setCursorVisible(hasFocus);
                }
            });

        } else {
            textView = new TextView(context);
        }
        textView.setId(View.generateViewId());

        textView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        textView.setRotation(angle);
        textView.setText(displayText);
        textView.setTextSize(textSize);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.circleConstraint = R.id.clock;
        layoutParams.circleAngle = angle;
        layoutParams.circleRadius = radius;

        textView.setLayoutParams(layoutParams);

        return textView;
    }

    /**
     * Creates and returns a dot imageView that is used to display a compass location.
     *
     * @param context The context of the activity in which the text view will be created.
     * @param angle The angle at which the text view will be rotated.
     * @param radius The radius of the circle in which the text view will be placed.
     * @return The text view that was created.
     */
    public static ImageView createCompassLocationImage(Context context, float angle, int radius) {
        ImageView imgView = new ImageView(context);
        imgView.setImageResource(R.drawable.dot);
        imgView.setScaleX(0.025f);
        imgView.setScaleY(0.025f);

        imgView.setId(View.generateViewId());

        imgView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        imgView.setRotation(angle);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imgView.getLayoutParams();
        layoutParams.circleConstraint = R.id.clock;
        layoutParams.circleAngle = angle;
        layoutParams.circleRadius = radius;

        imgView.setLayoutParams(layoutParams);

        ((ConstraintLayout) ((Activity) context).findViewById(R.id.clock)).addView(imgView);
        return imgView;
    }
}

