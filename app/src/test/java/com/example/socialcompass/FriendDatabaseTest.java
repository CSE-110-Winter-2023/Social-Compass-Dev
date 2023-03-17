package com.example.socialcompass;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FriendDatabaseTest {
    private idDao dao;
    private FriendDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, FriendDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.idDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsert() {
        userID item1 = new userID("1234");
        userID item2 = new userID("4321");

        long id1 = dao.insert(item1);
        long id2 = dao.insert(item2);

        // Check that these have all been inserted with unique IDs.
        assertNotEquals(id1, id2);
    }

    @Test
    public void testGet() {

        userID insertedItem = new userID("1234");
        long id = dao.insert(insertedItem);

        userID item = dao.get(id);
        assertEquals(id, item.id);
        assertEquals(insertedItem.friendID, item.friendID);
        assertEquals(insertedItem.myID, item.myID);
    }

    @Test
    public void testGetAll() {
        userID item1 = new userID("1234");
        userID item2 = new userID("5678");
        dao.insert(item1);
        dao.insert(item2);

        List<userID> dbItems = dao.getAll();

        assertEquals(2, dbItems.size());

        assertEquals(item1.friendID, dbItems.get(0).friendID);
        assertEquals(item1.myID, dbItems.get(0).myID);
        assertEquals(item2.friendID, dbItems.get(1).friendID);
        assertEquals(item2.myID, dbItems.get(1).myID);
    }

    @Test
    public void testGetAllLive() throws InterruptedException {

        userID item1 = new userID("1234");
        userID item2 = new userID("5678");
        dao.insert(item1);
        dao.insert(item2);

        LiveData<List<userID>> liveDataItems = dao.getAllLive();

        Observer<List<userID>> observer = new Observer<>() {
            @Override
            public void onChanged(List<userID> dbItems) {

                assertEquals(item1.friendID, dbItems.get(0).friendID);
                assertEquals(item1.myID, dbItems.get(0).myID);
                assertEquals(item2.friendID, dbItems.get(1).friendID);
                assertEquals(item2.myID, dbItems.get(1).myID);

                liveDataItems.removeObserver(this);
            }
        };

        liveDataItems.observeForever(observer);
        Thread.sleep(500);
    }


}
