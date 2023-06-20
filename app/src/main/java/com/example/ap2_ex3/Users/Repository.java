package com.example.ap2_ex3.Users;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.api.UserAPI;


public class Repository {
    UserAPI userAPI;

    public Repository(Application application) {
        userAPI = new UserAPI(application);
    }

    public LiveData<Boolean> createUser(CreateUserRequest createUserRequest){
        return userAPI.createUser(createUserRequest);
    }

    public LiveData<String> getToken(LoginRequest loginRequest){
        return userAPI.getToken(loginRequest);
    }

    public LiveData<User> getUser(String username, String token){
        return userAPI.getUser(username, token);
    }


}
