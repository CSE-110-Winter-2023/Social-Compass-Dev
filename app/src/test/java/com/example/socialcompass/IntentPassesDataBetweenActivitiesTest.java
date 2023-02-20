package com.example.socialcompass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.robolectric.Shadows.shadowOf;

import android.Manifest;
import android.content.Intent;
import android.widget.EditText;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;


@RunWith(RobolectricTestRunner.class)
public class IntentPassesDataBetweenActivitiesTest {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    float inputLat = -30;
    float inputLong = 16;
    float orientInputValue = 180;
    float defaultVal = -8888;
    String iputLabel = "john's house";
    final String orientOverrideKey = String.valueOf(R.string.orientOverride);
    final String parentLabelKey = String.valueOf(R.string.parentLabelKey);
    final String parentLatKey = String.valueOf(R.string.parentLatKey);
    final String parentLongKey = String.valueOf(R.string.parentLongKey);

    @Test
    public void testIntentPassesDataBetweenActivities() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup(); // Moves Activity to RESUMED state
            MainActivity activity = controller.get();

            //needed in order to be allowed to move to next activity
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));

            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));

            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));
            activity.findViewById(R.id.okBtn).performClick();

            Intent expectedIntent = new Intent(activity, CompassViewActivity.class);
            Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
            assertEquals(expectedIntent.getComponent(), actual.getComponent());

            //checking that orientation gets passed in
            float expectedOrient = actual.getFloatExtra(orientOverrideKey,defaultVal);
            assertEquals(orientInputValue, expectedOrient*180/Math.PI, 1);
            assertEquals(inputLat, actual.getFloatExtra(parentLatKey,defaultVal), 1);
            assertEquals(inputLong, actual.getFloatExtra(parentLongKey,defaultVal), 1);
            assertEquals(iputLabel, actual.getStringExtra(parentLabelKey));

        }
    }
}