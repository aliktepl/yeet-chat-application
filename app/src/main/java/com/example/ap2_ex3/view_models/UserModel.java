package com.example.ap2_ex3.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.repositories.UserRepo;

import java.util.List;


public class UserModel extends AndroidViewModel {

    private final UserRepo mRepository;
    private LiveData<Integer> status;
    private LiveData<String> token;
    private LiveData<List<User>> users;
    private LiveData<User> myUser;

    public UserModel(Application application){
        super(application);
        mRepository = new UserRepo(application);
        status = mRepository.getStatus();
        token = mRepository.getToken();
        users = mRepository.getUsers();
        myUser = mRepository.observeMyUser();
    }

    // User API Operations
    public void createUser(CreateUserRequest createUserRequest) {
        mRepository.createUserRequest(createUserRequest);
    }

    public LiveData<Integer> observeStatus() {
        return status;
    }

    public void getToken(LoginRequest loginRequest) {
        mRepository.tokenRequest(loginRequest);
    }

    public LiveData<String> observeToken() {
        return token;
    }

    public void getCurrUser(String username, String token) {
        mRepository.getUserRequest(username, token);
    }

    // User Dao operations
    public LiveData<List<User>> getUsers(){ return users; }

    public LiveData<User> getUser(String username) { return mRepository.getUser(username);}

    public LiveData<User> observeMyUser() { return myUser; }

    public List<User> getMyUser() { return mRepository.getMyUser(); }

    public void userDelete(User user) { mRepository.userDelete(user); }

}

