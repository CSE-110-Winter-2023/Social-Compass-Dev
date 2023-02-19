package com.example.socialcompass;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private SharedPreferences preferences;
    private MainActivity mainActivity;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        preferences = context.getSharedPreferences("test_preferences", Context.MODE_PRIVATE);
        mainActivity = new MainActivity();
        mainActivity.preferences = preferences;
        mainActivity.editor = preferences.edit();
    }

    @Test
    public void testCheckUser() {
        // First test without any saved preference
        assertFalse(mainActivity.hasHome());
        mainActivity.checkUser();
        assertFalse(mainActivity.hasHome());

        // Now save preferences
        mainActivity.saveLocation("Test Location", "test_label", 10.0f, "test_lat", 20.0f, "test_lng");

        // Test after saving preferences
        mainActivity.checkUser();
        assertTrue(mainActivity.hasHome());
    }

    @Test
    public void testSaveLocation() {
        // Check that no preferences exist initially
        assertFalse(preferences.getBoolean("hasLocation", false));

        // Save preferences
        mainActivity.saveLocation("Test Location", "test_label", 10.0f, "test_lat", 20.0f, "test_lng");

        // Check that preferences were saved
        assertTrue(preferences.getBoolean("hasLocation", false));
        assertEquals("Test Location", preferences.getString("test_label", ""));
        assertEquals(10.0f, preferences.getFloat("test_lat", 0.0f), 0.0f);
        assertEquals(20.0f, preferences.getFloat("test_lng", 0.0f), 0.0f);
    }
}
