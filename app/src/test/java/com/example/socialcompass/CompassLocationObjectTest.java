package com.example.socialcompass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import android.location.Location;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CompassLocationObjectTest {
    private CompassUIController controller;
    private CompassLocationObject compassLocationObject;
    private MockRemoteKey key1;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void setup() {
        controller = new CompassUIController(0, 0, 0, null, null);
        key1 = new MockRemoteKey("Location 1", new RemoteLocation("Location 1", 0, 0));
        compassLocationObject = new CompassLocationObject(key1, controller);
    }

    @Test
    public void testGetLocationName() {
        compassLocationObject.updateFromRemote(new RemoteLocation("Location 2", 0, 0));
        assertEquals("Location 2", compassLocationObject.getLocationName());
    }

    @Test
    public void testGetController() {
        assertSame(controller, compassLocationObject.getController());
    }

    @Test
    public void testSetController() {
        CompassUIController newController = new CompassUIController(90, 90, 10, null, null);
        compassLocationObject.setController(newController);
        assertSame(newController, compassLocationObject.getController());
    }
}
