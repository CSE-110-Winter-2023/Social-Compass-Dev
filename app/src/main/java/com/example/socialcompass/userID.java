package com.example.socialcompass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// developer.android.com/training/data-storage/room#java

@Entity(tableName = "user_ids")
public class userID {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String friendID;
    public static String myID = PreferencesActivity.getMyID();

    userID(@NonNull String friendID)
    {
        this.friendID = friendID;
    }

    @Override
    public String toString() {
        return "userID{" +
                "friendID='" + friendID + '\'' +
                ", myID=" + myID +
                '}';
    }
}
