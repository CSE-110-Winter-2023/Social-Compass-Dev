package com.example.socialcompass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Utilities {
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