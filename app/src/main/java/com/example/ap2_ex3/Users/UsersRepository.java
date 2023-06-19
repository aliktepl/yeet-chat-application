package com.example.ap2_ex3.Users;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class UsersRepository {
    private LiveData<List<User>> ulData;
    private UserDao userDao;

    public UsersRepository(Application application) {
        UserDB userDB = UserDB.getInstance(application);
        userDao = userDB.userDao();
        ulData = userDao.getAllUsers();
    }

    public LiveData<List<User>> getAll(){
        return ulData;
    }

    public void add(final User user){
        userDao.insert(user);
    }

    public User findUser(String username){
        return userDao.getUser(username);
    }
}
