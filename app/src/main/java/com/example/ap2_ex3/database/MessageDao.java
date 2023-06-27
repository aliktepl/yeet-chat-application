package com.example.ap2_ex3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_ex3.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM msg_table WHERE id = :id")
    LiveData<Message> getMessage(int id);
    @Query("SELECT * FROM msg_table")
    LiveData<List<Message>> getMessages();

    @Query("SELECT * FROM msg_table WHERE chatId= :chatId")
    List<Message> getMsgByChat(int chatId);

    @Query("DELETE FROM msg_table")
    void deleteAllMessages();

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);
}
