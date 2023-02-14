package com.example.socialcompass;

import static org.junit.Assert.assertNotEquals;

import android.Manifest;
import android.util.Pair;
import android.widget.TextView;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OrientationServiceTest {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testOrientationUpdate() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            MutableLiveData<Float> mockDataSource = new MutableLiveData<>();
            //getting the singleton
            OrientationService orientationService = activity.getOrientationService();
            orientationService.setMockOrientationSource(mockDataSource);

            Float start = 13F;
            Float end = 35F;
            mockDataSource.setValue(start);

            float before_rotation = activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation();
            float before_orientation = activity.getLocationContainer().getLocationAt(0).getController().getOrient();

            mockDataSource.setValue(end);

            float after_rotation = activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation();
            float after_orientation = activity.getLocationContainer().getLocationAt(0).getController().getOrient();

            assertNotEquals(before_rotation, after_rotation);
            assertNotEquals(before_orientation, after_orientation);
        });
    }
}