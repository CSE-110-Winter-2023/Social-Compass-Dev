package com.example.socialcompass;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompassControllerTest {
    private CompassController compassController;
    private float locAngle = 45;
    private float orientAngle = 90;
    private int distance = 10;

    @Before
    public void setUp() {
        compassController = new CompassController(locAngle, orientAngle, distance);
    }

    @Test
    public void testSetLocAngle() {
        float newLocAngle = 120;
        compassController.setLocAngle(newLocAngle);
        assertEquals(newLocAngle, compassController.getLocAngle(), 0);
    }

    @Test
    public void testSetOrient() {
        float newOrientAngle = 180;
        compassController.setOrient(newOrientAngle);
        assertEquals(newOrientAngle, compassController.getOrient(), 0);
    }
}
