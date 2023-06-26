package com.example.ap2_ex3.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.database.AppDB;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.api.UserAPI;

import java.util.List;


public class UserRepo {

    // Dao fields
    private UserDao userDao;

    // API fields
    private UserAPI userAPI;

    // Live Data fields
    private LiveData<User> user;
    private MutableLiveData<String> token;
    private MutableLiveData<Integer> status;


    public UserRepo(Application application) {
        // database init
        AppDB db = AppDB.getInstance(application);
        userDao = db.userDao();
        // api init
        userAPI = new UserAPI(userDao);
        // live data init
        user = userDao.getUser();
        status = new MutableLiveData<>();
        token = new MutableLiveData<>();
    }

    // User API operations
    public void createUserRequest(CreateUserRequest createUserRequest) {
        userAPI.createUser(createUserRequest, status);
    }

    public void tokenRequest(LoginRequest loginRequest) {
        userAPI.getToken(loginRequest, token, status);
    }

    public void getUserRequest(String username, String token) {
        userAPI.getUser(username, token, status);
    }

    // Live data listeners
    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    public MutableLiveData<String> getToken(){ return token; }

    // User dao operations
    public LiveData<User> getUser() {
        return user;
    }

}
