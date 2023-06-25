package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.database.AppDB;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.api.UserAPI;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UserRepo {

    // Dao fields
    private UserDao userDao;

    // API fields
    private UserAPI userAPI;

    // Live Data fields
    private LiveData<List<User>> users;
    private MutableLiveData<User> myUser;
    private MutableLiveData<String> token;
    private MutableLiveData<Integer> status;


    public UserRepo(Application application) {
        // database init
        AppDB db = AppDB.getInstance(application);
        userDao = db.userDao();
        // api init
        userAPI = new UserAPI(userDao);
        // live data init
        users = userDao.getAllUsers();
        myUser = new MutableLiveData<>();
        token = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    // User API operations
    public void createUserRequest(CreateUserRequest createUserRequest) {
        userAPI.createUser(createUserRequest, status);
    }

    public void tokenRequest(LoginRequest loginRequest) {
        userAPI.getToken(loginRequest, token, status);
    }

    public void getUserRequest(String username, String token) {
        userAPI.getUser(username, token, myUser);
    }

    public void userDelete(User user) {
        userDao.delete(user);
    }

    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    public MutableLiveData<String> getToken() {
        return token;
    }

    // User dao operations
    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<User> getUser(String username) {
        return userDao.getUser(username);
    }

    public LiveData<User> observeMyUser() {
        return myUser;
    }

    public List<User> getMyUser() {
        return userDao.getMyUser();
    }
}
