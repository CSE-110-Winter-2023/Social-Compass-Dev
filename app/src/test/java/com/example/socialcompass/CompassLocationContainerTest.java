package com.example.socialcompass;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import android.content.Context;
import android.widget.TextView;

public class CompassLocationContainerTest {
    private CompassLocationContainer emptyContainer;
    private CompassLocationContainer containerWithTwoElements;

    @Before
    public void setUp() {
        emptyContainer = new CompassLocationContainer();
        containerWithTwoElements = new CompassLocationContainer();

        CompassUIController controller1 = new CompassUIController(0, 0, 0, null);
        containerWithTwoElements.addLocation(new CompassLocationObject("location 1", null, controller1));

        CompassUIController controller2 = new CompassUIController(0, 0, 0, null);
        containerWithTwoElements.addLocation(new CompassLocationObject("location 2", null, controller2));

    }

    @Test
    public void testAddLocation() {
        CompassUIController controller = new CompassUIController(0, 0, 0, null);
        CompassLocationObject location = new CompassLocationObject("Test Location", null, controller);

        emptyContainer.addLocation(location);

        assertEquals(1, emptyContainer.getLocationCount());
        assertEquals(location, emptyContainer.getLocationAt(0));
    }

    @Test
    public void testGetLocationCount() {
        assertEquals(0, emptyContainer.getLocationCount());
        assertEquals(2, containerWithTwoElements.getLocationCount());
    }

    @Test
    public void testGetLocationAt() {
        CompassUIController controller1 = new CompassUIController(0, 0, 0, null);
        CompassLocationObject location1 = new CompassLocationObject("Test Location 1", null, controller1);
        containerWithTwoElements.addLocation(location1);

        CompassUIController controller2 = new CompassUIController(0, 0, 0, null);
        CompassLocationObject location2 = new CompassLocationObject("Test Location 2", null, controller2);
        containerWithTwoElements.addLocation(location2);

        assertEquals(location1, containerWithTwoElements.getLocationAt(2));
        assertEquals(location2, containerWithTwoElements.getLocationAt(3));
    }

    @Test
    public void testRemoveLocationAt() {
        containerWithTwoElements.removeLocationAt(0);

        assertEquals(1, containerWithTwoElements.getLocationCount());
        assertNotEquals("Location 1", containerWithTwoElements.getLocationAt(0).getLocationName());
    }

    @Test
    public void testClearLocations() {
        containerWithTwoElements.clearLocations();
        assertEquals(0, containerWithTwoElements.getLocationCount());
    }

    @Test
    public void testGetAllLocations() {
        assertEquals(0, emptyContainer.getAllLocations().size());
        assertEquals(2, containerWithTwoElements.getAllLocations().size());
    }


}


