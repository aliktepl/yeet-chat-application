package com.example.ap2_ex3.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.repositories.UserRepo;

public class UserModel extends AndroidViewModel {

    private final UserRepo mRepository;
    private LiveData<Integer> status;
    private LiveData<String> token;
    private LiveData<User> user;

    public UserModel(Application application) {
        super(application);
        mRepository = new UserRepo(application);
        status = mRepository.getStatus();
        token = mRepository.getToken();
        user = mRepository.getUser();
    }

    // User API Operations
    public void createUser(CreateUserRequest createUserRequest) {
        mRepository.createUserRequest(createUserRequest);
    }

    public void getToken(LoginRequest loginRequest) {
        mRepository.tokenRequest(loginRequest);
    }

    public void getCurrUser(String username, String token) {
        mRepository.getUserRequest(username, token);
    }

    // Live data listeners
    public LiveData<Integer> observeStatus() {
        return status;
    }

    public LiveData<String> observeToken() {
        return token;
    }

    // User Dao operations
    public LiveData<User> getUser() {
        return user;
    }

}

