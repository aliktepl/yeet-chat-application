package com.example.ap2_ex3.Users;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.api.User;


public class ViewModel extends androidx.lifecycle.ViewModel {

    private final Repository mRepository;
    private LiveData<Boolean> isUserCreated;
    private LiveData<String> token;
    private LiveData<User> user;

    public ViewModel(Application application){
        mRepository = new Repository(application);
    }

    // Observe isUserCreated LiveData
    public LiveData<Boolean> createUser(CreateUserRequest createUserRequest) {
        isUserCreated = mRepository.createUser(createUserRequest);
        return isUserCreated;
    }

    public LiveData<Boolean> observeCreated() {
        return isUserCreated;
    }

    // Observe token LiveData
    public LiveData<String> getToken(LoginRequest loginRequest) {
        token = mRepository.getToken(loginRequest);
        return token;
    }

    public LiveData<String> observeToken() {
        return token;
    }

    // Observe user LiveData
    public LiveData<User> getUser(String username, String token) {
        user = mRepository.getUser(username, token);
        return user;
    }

    public LiveData<User> observeUser() {
        return user;
    }
}
