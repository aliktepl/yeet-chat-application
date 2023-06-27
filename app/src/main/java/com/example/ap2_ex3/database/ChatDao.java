package com.example.ap2_ex3.database;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_ex3.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Chat... chat);

    @Update
    void Update(Chat... chat);

    @Delete
    void Delete(Chat... chat);

    @Query("SELECT * FROM chat_table ORDER BY id ASC")
    LiveData<List<Chat>> getAllChats();
    @Query("SELECT * FROM chat_table WHERE id=:id")
    Chat getChat(int id);
    @Query("UPDATE chat_table SET lstMsgContent = :newMsgContent, lstMsgTime = :newMsgTime WHERE id = :chatId")
    void updateLastMessage(int chatId, String newMsgContent, String newMsgTime);
    @Query("DELETE FROM chat_table")
    void deleteAllChats();
}
