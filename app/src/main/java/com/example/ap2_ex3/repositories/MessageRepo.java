package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api.ChatAPI;
import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.database.AppDB;
import com.example.ap2_ex3.database.MessageDao;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.api.UserAPI;
import com.example.ap2_ex3.database.ChatDao;
import com.example.ap2_ex3.entities.Chat;

import java.util.List;


public class MessageRepo {

    // Dao fields
    private UserDao userDao;

    private MessageDao messageDao;

    // API fields

    // Live Data fields
    private LiveData<List<Message>> messages;
    private MutableLiveData<Integer> status;


    public MessageRepo(Application application) {
        // database init
        AppDB db = AppDB.getInstance(application);
        userDao = db.userDao();
        messageDao = db.messageDao();
        // api init

        // live data init
        messages = messageDao.getMessages();
        status = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    // Message dao operations

    public LiveData<List<Message>> getMessages() { return messages; }

    public LiveData<Message> getMessage(int id) { return messageDao.getMessage(id); }

}
