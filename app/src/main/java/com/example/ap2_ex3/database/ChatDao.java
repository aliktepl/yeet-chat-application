package com.example.ap2_ex3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_ex3.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    void Insert(Chat chat);

    @Update
    void Update(Chat chat);

    @Delete
    void Delete(Chat chat);

    @Query("SELECT * FROM Chat ORDER BY id ASC")
    LiveData<List<Chat>> getAllChats();
}
