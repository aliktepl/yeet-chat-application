package com.example.ap2_ex3.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;


public class ViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private LiveData<Integer> status;
    private LiveData<String> token;
    private LiveData<User> user;
    private LiveData<List<Chat>> chats;

    public ViewModel(Application application){
        super(application);
        mRepository = new Repository(application);
        status = mRepository.getStatus();
        token = mRepository.getToken();
        user = mRepository.getUser();
        chats = mRepository.getAllChats();
    }

    // User Operations
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

    public void getUser(String username, String token) {
        mRepository.getUserRequest(username, token);
    }

    public LiveData<User> observeUser() {
        return user;
    }

    public LiveData<List<Chat>> getChats() {
        return chats;
    }

    // Chat operations
    public void Insert(Chat chat) {
        mRepository.insert(chat);
    }

    public void Delete(Chat chat) {
        mRepository.delete(chat);
    }

    public void Update(Chat chat) {
        mRepository.update(chat);
    }
}
