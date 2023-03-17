package com.example.socialcompass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.Manifest;
import android.util.Pair;

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
public class LocationServiceTest {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testGetLocation() {
        ActivityScenario<CompassViewActivity> scenario = ActivityScenario.launch(CompassViewActivity.class);
        LocationAPI.useMockAPI(true);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            MutableLiveData<Pair<Double, Double>> mockDataSource = new MutableLiveData<>();
            activity.getLocationService().setMockLocationSource(mockDataSource);

            Pair<Double, Double> start = new Pair<>(-73.0000, 2.0000);
            Pair<Double, Double> end = new Pair<>(81.0000, 2.0000);

            mockDataSource.setValue(start);
            assertEquals(start, activity.getLocationService().getLocation().getValue());

            mockDataSource.setValue(end);
            assertEquals(end, activity.getLocationService().getLocation().getValue());
        });
    }
}