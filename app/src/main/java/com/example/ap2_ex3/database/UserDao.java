package com.example.ap2_ex3.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap2_ex3.entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table LIMIT 1")
    LiveData<User> getUser();

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("SELECT * FROM user_table LIMIT 1")
    User getUserObject();

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

}
