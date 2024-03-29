package com.example.socialcompass;

import static org.junit.Assert.assertEquals;

import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CompassUIControllerTest {
    private CompassUIController compassUIController;

    @Rule
    public GrantPermissionRule mRuntimePermissionRuleF = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule mRuntimePermissionRuleC = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_COARSE_LOCATION);


    @Before
    public void setup() {
        compassUIController = new CompassUIController(0, 0, 0, null);
    }

    @Test
    public void testGetLocAngle() {
        assertEquals(0, compassUIController.getLocAngle(), 0.001);
    }

    @Test
    public void testSetLocAngle() {
        assertEquals(0, compassUIController.getLocAngle(), 0.001);
        compassUIController.setLocAngle(90);
        assertEquals(90, compassUIController.getLocAngle(), 0.001);
    }

    @Test
    public void testGetOrient() {
        assertEquals(0, compassUIController.getOrient(), 0.001);
    }

    @Test
    public void testSetOrient() {
        assertEquals(0, compassUIController.getOrient(), 0.001);
        compassUIController.setOrient(90);
        assertEquals(90, compassUIController.getOrient(), 0.001);
    }
}
