package com.example.socialcompass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

// developer.android.com/training/data-storage/room#java

@Entity(tableName = "user_ids")
public class userID {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    public String friendID;
    public String myID = UUID.randomUUID().toString();

    userID(@NonNull String friendID)
    {
        this.friendID = friendID;
    }



}
