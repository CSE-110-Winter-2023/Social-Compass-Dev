package com.example.socialcompass;

import static org.junit.Assert.assertEquals;

import android.Manifest;
import android.app.Dialog;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;

@RunWith(RobolectricTestRunner.class)
public class UtilitiesTestRobolectric {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Test
    public void testShowAlter() {
        final String testAlterStr = "testAlertStr";

        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        Dialog expected = Utilities.showAlert(mainActivity, testAlterStr);

        Dialog actual = ShadowAlertDialog.getShownDialogs().get(0);

        assertEquals(expected, actual);


    }

    @Test
    public void testCreateCompassLocationTextView(){

    }
}
