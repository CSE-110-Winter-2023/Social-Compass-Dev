package com.example.socialcompass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.Manifest;
import android.app.Dialog;
import android.widget.EditText;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowDialog;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class UIaddLocationTests {

    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();



    //******************************tests for OK button *********************************
    @Test
    public void testUIAddingLocationNoProblem() {
        float inputLat = -30;
        float inputLong = -6;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            List<Dialog> actual = ShadowDialog.getShownDialogs();
            assertEquals(actual.size(), 0);
        });
    }


    @Test
    public void testUIAddingLocationInvalidLong() {
        float inputLat = -30;
        float inputLong = -300;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationInvalidLat() {
        float inputLat = -300;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationInvalidOrient() {
        float inputLat = 0;
        float inputLong = 0;
        float orientInputValue = 370;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationMissingLat() {
        float inputLat = 0;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
//            EditText parentLat = activity.findViewById(R.id.parentLat);
//            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationMissingLong() {
        float inputLat = -10;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
//            EditText homeLong = activity.findViewById((R.id.parentLong));
//            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationMissingOrient() {
        float inputLat = -10;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

//            EditText orientInput = activity.findViewById(R.id.orientInput);
//            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.okBtn).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    //******************************tests for Submit button *********************************
    @Test
    public void testUIAddingLocationSubmitNoProblem() {
        float inputLat = -30;
        float inputLong = -6;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            List<Dialog> actual = ShadowDialog.getShownDialogs();
            assertEquals(actual.size(), 0);
        });
    }


    @Test
    public void testUIAddingLocationSubmitInvalidLong() {
        float inputLat = -30;
        float inputLong = -300;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationSubmitInvalidLat() {
        float inputLat = -300;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationSubmitInvalidOrient() {
        float inputLat = 0;
        float inputLong = 0;
        float orientInputValue = 370;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            List<Dialog> actual = ShadowDialog.getShownDialogs();
            assertEquals(actual.size(), 0);
        });
    }

    @Test
    public void testUIAddingLocationSubmitMissingLat() {
        float inputLat = 0;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
//            EditText parentLat = activity.findViewById(R.id.parentLat);
//            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationSubmitMissingLong() {
        float inputLat = -10;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
//            EditText homeLong = activity.findViewById((R.id.parentLong));
//            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

            EditText orientInput = activity.findViewById(R.id.orientInput);
            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            Dialog actual = ShadowDialog.getShownDialogs().get(0);
            assertTrue(actual.isShowing());
        });
    }

    @Test
    public void testUIAddingLocationSubmitMissingOrient() {
        float inputLat = -10;
        float inputLong = 0;
        float orientInputValue = 180;
        String iputLabel = "john's house";

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            EditText parentLat = activity.findViewById(R.id.parentLat);
            parentLat.setText(String.valueOf(inputLat));
            EditText homeLong = activity.findViewById((R.id.parentLong));
            homeLong.setText(String.valueOf(inputLong));
            EditText homelabel = activity.findViewById((R.id.parentLabel));
            homelabel.setText(iputLabel);

//            EditText orientInput = activity.findViewById(R.id.orientInput);
//            orientInput.setText(String.valueOf(orientInputValue));

            activity.findViewById(R.id.submitButton).performClick();

            List<Dialog> actual = ShadowDialog.getShownDialogs();
            assertEquals(actual.size(), 0);
        });
    }

}



