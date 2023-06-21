package com.example.ap2_ex3.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.api.User;


public class ViewModel extends androidx.lifecycle.ViewModel {

    private final Repository mRepository;
    private LiveData<Integer> status;
    private LiveData<String> token;
    private LiveData<User> user;

    public ViewModel(){
        mRepository = new Repository();
        status = mRepository.getStatus();
        token = mRepository.getToken();
        user = mRepository.getUser();
    }

    // Observe isUserCreated LiveData
    public void createUser(CreateUserRequest createUserRequest) {
        mRepository.createUserRequest(createUserRequest);
    }

    public LiveData<Integer> observeStatus() {
        return status;
    }

    // Observe token LiveData
    public void getToken(LoginRequest loginRequest) {
        mRepository.tokenRequest(loginRequest);
    }

    public LiveData<String> observeToken() {
        return token;
    }

    // Observe user LiveData
    public void getUser(String username, String token) {
        mRepository.getUserRequest(username, token);
    }

    public LiveData<User> observeUser() {
        return user;
    }
}
