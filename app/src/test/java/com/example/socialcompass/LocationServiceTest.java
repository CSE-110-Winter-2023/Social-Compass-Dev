package com.example.socialcompass;

import static org.junit.Assert.assertNotEquals;

import android.Manifest;
import android.util.Pair;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LocationServiceTest {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Test
    public void testLocationsUpdate() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            MutableLiveData<Pair<Double, Double>> mockDataSource = new MutableLiveData<>();

            activity.getLocationService().setMockLocationSource(mockDataSource);

            Pair<Double, Double> start = new Pair<>(-73.0000, 2.0000);
            Pair<Double, Double> end = new Pair<>(81.0000, 2.0000);

            mockDataSource.postValue(start);

            // Add a delay to allow the location to update
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            float rotation_before = activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation();

            mockDataSource.postValue(end);

            // Add a delay to allow the location to update
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            float rotation_after = activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation();

            assertNotEquals(rotation_before, rotation_after, 0.01);
        });
    }
}
