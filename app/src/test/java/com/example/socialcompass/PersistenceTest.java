package com.example.socialcompass;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PersistenceTest {
    final String parentLabelKey = String.valueOf(R.string.parentLabelKey);
    final String parentLatKey = String.valueOf(R.string.parentLatKey);
    final String parentLongKey = String.valueOf(R.string.parentLongKey);

    @Test
    public void testSaveDataToPersistentStorage() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            EditText nameEditText = activity.findViewById(R.id.parentLabel);
            nameEditText.setText("TestHome");
            EditText latEditText = activity.findViewById(R.id.parentLat);
            latEditText.setText("-80");
            EditText longEditText = activity.findViewById(R.id.parentLong);
            longEditText.setText("20");
            Button submitButton = activity.findViewById(R.id.submitButton);
            submitButton.performClick();

            // Verify that the data is stored in persistent storage
            SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
            String savedLabel = sharedPreferences.getString(parentLabelKey, "FAIL");
            float savedLat = sharedPreferences.getFloat(parentLatKey, -8888);
            float savedLong = sharedPreferences.getFloat(parentLongKey, -8888);
            assertEquals("TestHome", savedLabel);
            assertEquals(-80, savedLat, 0.01);
            assertEquals(20, savedLong, 0.01);
        });

    }
}
