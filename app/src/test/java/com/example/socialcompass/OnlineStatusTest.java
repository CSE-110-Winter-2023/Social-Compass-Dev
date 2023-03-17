package com.example.socialcompass;
import static com.example.socialcompass.GPSTimer.calculateGPSdelay;
import static com.example.socialcompass.GPSTimer.color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.Manifest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
@RunWith(RobolectricTestRunner.class)
public class OnlineStatusTest {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void testImageViewFilter() {
        long mLastGpsTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        ImageView status = new ImageView(ApplicationProvider.getApplicationContext());
        status.setImageResource(R.id.status);
        if(currentTime - mLastGpsTime == 0) {
            assertNull(status.getColorFilter());
        }

    }

    @Test
    public void testTextViewTextChange() {
        ActivityScenario<CompassViewActivity> scenario = ActivityScenario.launch(CompassViewActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            TextView timer = activity.findViewById(R.id.timer);

            activity.mLastGpsTime = 0;
            activity.currentTime = 0;
            assertEquals("", timer.getText().toString());

            activity.currentTime = 100000;
            assertEquals("1 m", calculateGPSdelay(activity.currentTime, activity.mLastGpsTime));

            activity.currentTime = 3000000;
            assertEquals("50 m", calculateGPSdelay(activity.currentTime, activity.mLastGpsTime));

            activity.currentTime = 10800000;
            assertEquals("3 hr", calculateGPSdelay(activity.currentTime, activity.mLastGpsTime));


        });
    }

    @Test
    public void testColorChange() {
        ActivityScenario<CompassViewActivity> scenario = ActivityScenario.launch(CompassViewActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            activity.mLastGpsTime = 0;
            activity.currentTime = 0;
            assertEquals(true, color(activity.currentTime, activity.mLastGpsTime));

            activity.currentTime = 100000;
            assertEquals(false, color(activity.currentTime, activity.mLastGpsTime));

            activity.currentTime = 3000000;
            assertEquals(false, color(activity.currentTime, activity.mLastGpsTime));

            activity.currentTime = 10800000;
            assertEquals(false, color(activity.currentTime, activity.mLastGpsTime));


        });

    }
}
