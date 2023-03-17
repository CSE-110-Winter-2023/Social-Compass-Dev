package com.example.socialcompass;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

// Most of this code is taken from Lab 6, but modified to be used for this project.

public class PreferencesActivityTest {

    FriendDatabase friendDb;
    idDao iddao;

    @Before
    public void resetDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        friendDb = Room.inMemoryDatabaseBuilder(context, FriendDatabase.class)
                .allowMainThreadQueries()
                .build();
        FriendDatabase.injectTestDatabase(friendDb);
        iddao = friendDb.idDao();
    }

    @Test
    public void testAddOneFriend() {
        String newText = "Ensure all tests pass";

        ActivityScenario<PreferencesActivity> scenario
                = ActivityScenario.launch(PreferencesActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            List<userID> beforeFriends = iddao.getAll();

            EditText newFriend = activity.findViewById(R.id.editUUID);
            TextView addFriendButton = activity.findViewById(R.id.add_Btn);

            newFriend.setText(newText);
            addFriendButton.performClick();

            List<userID> afterFriends = iddao.getAll();

            assertEquals(beforeFriends.size() + 1, afterFriends.size());
            assertEquals(newText, afterFriends.get(afterFriends.size() - 1).friendID);
        });
    }

    @Test
    public void testAddTwoFriends() {
        String newText = "Ensure all tests pass";
        String newText2 = "Ensure this also passes";

        ActivityScenario<PreferencesActivity> scenario
                = ActivityScenario.launch(PreferencesActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            List<userID> beforeFriends = iddao.getAll();

            EditText newFriend = activity.findViewById(R.id.editUUID);
            TextView addFriendButton = activity.findViewById(R.id.add_Btn);

            newFriend.setText(newText);
            addFriendButton.performClick();

            EditText newFriend2 = activity.findViewById(R.id.editUUID);

            newFriend2.setText(newText2);
            addFriendButton.performClick();

            List<userID> afterFriends = iddao.getAll();

            assertEquals(beforeFriends.size() + 2, afterFriends.size());
            assertEquals(newText, afterFriends.get(afterFriends.size() - 2).friendID);
            assertEquals(newText2, afterFriends.get(afterFriends.size() - 1).friendID);
            assertEquals(afterFriends.get(afterFriends.size() - 2).myID,
                    afterFriends.get(afterFriends.size() - 1).myID);
        });
    }
}
