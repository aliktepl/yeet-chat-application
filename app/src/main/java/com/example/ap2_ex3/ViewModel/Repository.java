package com.example.ap2_ex3.ViewModel;

import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.api.UserAPI;


public class Repository {
    UserAPI userAPI;
    MutableLiveData<String> token;
    MutableLiveData<User> currUser;
    MutableLiveData<Integer> status;

    public Repository() {
        userAPI = new UserAPI();
        token = new MutableLiveData<>();
        currUser = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void createUserRequest(CreateUserRequest createUserRequest){
         userAPI.createUser(createUserRequest, status);
    }

    public void tokenRequest(LoginRequest loginRequest){
        userAPI.getToken(loginRequest, token);
    }

    public void getUserRequest(String username, String token){
        userAPI.getUser(username, token, currUser);
    }

    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    public MutableLiveData<String> getToken() {
        return token;
    }

    public MutableLiveData<User> getUser() {
        return currUser;
    }

}
