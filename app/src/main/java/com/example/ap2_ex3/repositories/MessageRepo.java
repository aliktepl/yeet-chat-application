package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api.ChatAPI;
import com.example.ap2_ex3.api.MessageAPI;
import com.example.ap2_ex3.api_requests.CreateUserRequest;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.api_requests.MessageRequest;
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
    private MessageAPI messageAPI;

    // Live Data fields
    private LiveData<List<Message>> messages;
    private MutableLiveData<Integer> status;

    private String token;


    public MessageRepo(Application application) {
        // database init
        AppDB db = AppDB.getInstance(application);
        userDao = db.userDao();
        messageDao = db.messageDao();
        // api init
        messageAPI = new MessageAPI(messageDao);
        // live data init
        messages = messageDao.getMessages();
        status = new MutableLiveData<>();
    }

    // live data listeners
    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    public void setToken(String token) { this.token = token; }

    // Message dao operations

    public LiveData<List<Message>> getMessages() { return messages; }

    public List<Message> getMessagesByChat(Integer id) { return messageDao.getMsgByChat(id); }

    public LiveData<Message> getMessage(int id) { return messageDao.getMessage(id); }

    // api operations
    public void getMessagesRequest(Integer chatId){ messageAPI.getMessages(chatId, status, token); }

    public void createMessageRequest(Integer chatId, String msgContent){
        messageAPI.createMessage(chatId, msgContent, token, status);
    }

}
