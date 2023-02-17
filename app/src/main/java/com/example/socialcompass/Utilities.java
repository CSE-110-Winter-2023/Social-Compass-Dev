package com.example.socialcompass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Utilities extends Activity {

    public static void showAlert(Activity activity, String message) {
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
    }

    public static boolean checkLatitude(float lat){
        if(lat <= -90 || lat >= 90){
            return false;
        }
        return true;
    }
    public static boolean checkLongitude(float Long){
        if(Long <= -180 || Long > 180){
            return false;
        }
        return true;
    }

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
}

