package com.example.socialcompass;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FriendViewModel extends AndroidViewModel {

    private LiveData<List<userID>> friendIDs;
    private final idDao id_dao;


    public FriendViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        FriendDatabase db = FriendDatabase.getSingleton(context);
        id_dao = db.idDao();
    }

    public List<userID> getFriendsSync() {
        return id_dao.getAll();
    }

    public LiveData<List<userID>> getFriends() {
        if (friendIDs == null) {
            loadFriends();
        }
        return friendIDs;
    }

    public void addFriend(String text) {
        userID newItem = new userID(text);
        Log.i("[HERE]", text);
        id_dao.insert(newItem);

    }
    private void loadFriends() {
        friendIDs = id_dao.getAllLive();
    }
}
