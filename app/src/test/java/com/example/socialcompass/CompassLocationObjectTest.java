package com.example.socialcompass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

public class CompassLocationObjectTest {
    private CompassUIController controller;
    private Location location;
    private CompassLocationObject compassLocationObject;

    @Before
    public void setup() {
        controller = new CompassUIController(0, 0, 0, null);
        location = new Location("");
        compassLocationObject = new CompassLocationObject("Test Location", location, controller);
    }

    @Test
    public void testGetLocationName() {
        assertEquals("Test Location", compassLocationObject.getLocationName());
    }

    @Test
    public void testSetLocationName() {
        compassLocationObject.setLocationName("New Location");
        assertEquals("New Location", compassLocationObject.getLocationName());
    }

    @Test
    public void testGetLocation() {
        assertSame(location, compassLocationObject.getLocation());
    }

    @Test
    public void testSetLocation() {
        Location newLocation = new Location("Another Location");
        compassLocationObject.setLocation(newLocation);
        assertSame(newLocation, compassLocationObject.getLocation());
    }

    @Test
    public void testGetController() {
        assertSame(controller, compassLocationObject.getController());
    }

    @Test
    public void testSetController() {
        CompassUIController newController = new CompassUIController(90, 90, 10, null);
        compassLocationObject.setController(newController);
        assertSame(newController, compassLocationObject.getController());
    }
}
