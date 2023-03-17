package com.example.socialcompass;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface idDao {
    @Insert
    long insert(userID id);

    @Insert
    List<Long> insertAll(List<userID> id);

    @Query("SELECT * FROM `user_ids` WHERE `id`=:id")
    userID get(long id);

    @Query("SELECT * FROM `user_ids`")
    List<userID> getAll();

    @Query("SELECT * FROM  `user_ids`")
    LiveData<List<userID>> getAllLive();

}
