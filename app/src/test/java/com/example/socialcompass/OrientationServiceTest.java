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
public class OrientationServiceTest {
    @Rule
    public GrantPermissionRule fRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule cRuntimePermissionRule = GrantPermissionRule
            .grant(Manifest.permission.ACCESS_COARSE_LOCATION);

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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("mockValue start " + mockDataSource.getValue());
            System.out.println("tv start " + activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation());
            System.out.println("orient start " + activity.getLocationContainer().getLocationAt(0).getController().getOrient());

//            CompassLocationContainer container = activity.getLocationContainer();


            mockDataSource.setValue(end);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("mockValue end " + mockDataSource.getValue());
            System.out.println("tv end " + activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation());
            System.out.println("orient end " + activity.getLocationContainer().getLocationAt(0).getController().getOrient());

        });
    }
}

//     System.out.println(activity.getLocationContainer().getLocationAt(0).getLocationName());
//             System.out.println(activity.getLocationContainer().getLocationAt(0).getLocation().getLongitude());
//             System.out.println(activity.getLocationContainer().getLocationAt(0).getLocation().getLatitude());
//             System.out.println("UI" + activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation());
//
//             activity.getLocationContainer().getLocationAt(0).getLocation().setLongitude(0);
//             activity.getLocationContainer().getLocationAt(0).getLocation().setLatitude(0);
//
//
//
//             System.out.println(activity.getLocationContainer().getLocationAt(0).getController().getLocAngle());
//             System.out.println(activity.getLocationContainer().getLocationAt(0).getController().getOrient());
//             System.out.println("UI" + activity.getLocationContainer().getLocationAt(0).getController().getTextView().getRotation());

