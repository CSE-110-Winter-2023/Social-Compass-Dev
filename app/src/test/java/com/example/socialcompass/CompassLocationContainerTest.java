package com.example.socialcompass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CompassLocationContainerTest {
    private CompassLocationContainer emptyContainer;
    private CompassLocationContainer containerWithTwoElements;
    private MockRemoteKey key1;
    private MockRemoteKey key2;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void setUp() {
        emptyContainer = new CompassLocationContainer();
        containerWithTwoElements = new CompassLocationContainer();

        key1 = new MockRemoteKey("Location 1", new RemoteLocation("Location 1", 0, 0));
        key2 = new MockRemoteKey("Location 2", new RemoteLocation("Location 2", 50, 50));


        CompassUIController controller1 = new CompassUIController(0, 0, 0, null);
        containerWithTwoElements.addLocation(new CompassLocationObject(key1, controller1));

        CompassUIController controller2 = new CompassUIController(0, 0, 0, null);
        containerWithTwoElements.addLocation(new CompassLocationObject( key2, controller2));

    }

    @Test
    public void testAddLocation() {
        CompassUIController controller = new CompassUIController(0, 0, 0, null);
        CompassLocationObject location = new CompassLocationObject(key1, controller);

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
        CompassLocationObject location1 = new CompassLocationObject(key1, controller1);
        containerWithTwoElements.addLocation(location1);

        CompassUIController controller2 = new CompassUIController(0, 0, 0, null);
        CompassLocationObject location2 = new CompassLocationObject(key2, controller2);
        containerWithTwoElements.addLocation(location2);

        assertEquals(location1, containerWithTwoElements.getLocationAt(2));
        assertEquals(location2, containerWithTwoElements.getLocationAt(3));
    }

    @Test
    public void testRemoveLocationAt() {
        containerWithTwoElements.removeLocationAt(0);

        assertEquals(1, containerWithTwoElements.getLocationCount());
        assertNotEquals("Location 2", containerWithTwoElements.getLocationAt(0).getLocationName());
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


