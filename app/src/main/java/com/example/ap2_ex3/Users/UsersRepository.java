package com.example.ap2_ex3.Users;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class UsersRepository {
    private UserListData ulData;
    private UserDB userDB;
    private UserDao userDao;

    public UsersRepository(Context context) {
        userDB = Room.databaseBuilder(context, UserDB.class, "user_database")
                .build();
        userDao = userDB.userDao();
    }

    class UserListData extends MutableLiveData<List<User>>{
        public UserListData(){
            super();
            setValue(new ArrayList<>());
        }

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
