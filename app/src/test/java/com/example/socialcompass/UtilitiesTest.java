package com.example.socialcompass;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilitiesTest {
    @Test
    public void valid_latInput_equals_90() {
        assertEquals(false, Utilities.checkLatitude(90));
    }

    @Test
    public void valid_latInput_near_90() {
        assertEquals(false, Utilities.checkLatitude((float)90.1));
        assertEquals(true, Utilities.checkLatitude((float)(89.9)));
    }

    @Test
    public void valid_latInput_equals_negative_90() {
        assertEquals(true, Utilities.checkLatitude(-90));
    }

    @Test
    public void valid_latInput_near_negative_90() {
        assertEquals(false, Utilities.checkLatitude((float)-90.1));
        assertEquals(true, Utilities.checkLatitude((float)(-89.9)));
    }

    @Test
    public void valid_longInput_equals_180() {
        assertEquals(false, Utilities.checkLongitude(180));
    }

    @Test
    public void valid_latInput_near_180() {
        assertEquals(false, Utilities.checkLongitude((float)180.1));
        assertEquals(true, Utilities.checkLongitude((float)(179.9)));
    }

    @Test
    public void valid_longInput_negative_180() {
        assertEquals(true, Utilities.checkLongitude(-180));
    }
}
