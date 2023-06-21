package com.example.ap2_ex3.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.api.UserAPI;
import com.example.ap2_ex3.database.ChatDB;
import com.example.ap2_ex3.database.ChatDao;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;


public class Repository {
    private ChatDao dao;

    private LiveData<List<Chat>> allChats;
    private UserAPI userAPI;
    private MutableLiveData<String> token;
    private MutableLiveData<User> currUser;
    private MutableLiveData<Integer> status;

    public Repository(Application application) {
        userAPI = new UserAPI();
        ChatDB db = ChatDB.getInstance(application);
        dao = db.chatDao();
        allChats = dao.getAllChats();
        token = new MutableLiveData<>();
        currUser = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    // User operations
    public void createUserRequest(CreateUserRequest createUserRequest) {
        userAPI.createUser(createUserRequest, status);
    }

    public void tokenRequest(LoginRequest loginRequest) {
        userAPI.getToken(loginRequest, token, status);
    }

    public void getUserRequest(String username, String token) {
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

    //
    public void insert(Chat chat) {
        new Repository.InsertChatAsyncTask(dao).execute(chat);
    }

    public void update(Chat chat) {
        new Repository.UpdateChatAsyncTask(dao).execute(chat);
    }

    public void delete(Chat chat) {
        new Repository.DeleteChatAsyncTask(dao).execute(chat);
    }

    public LiveData<List<Chat>> getAllChats() {
        return allChats;
    }

    private static class InsertChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao dao;
        private InsertChatAsyncTask(ChatDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            dao.Insert(chats[0]);
            return null;
        }
    }

    private static class UpdateChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao dao;
        private UpdateChatAsyncTask(ChatDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            dao.Update(chats[0]);
            return null;
        }
    }

    private static class DeleteChatAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao dao;
        private DeleteChatAsyncTask(ChatDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Chat... chats) {
            dao.Delete(chats[0]);
            return null;
        }
    }

}
