package com.example.ap2_ex3.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.api.MessageAPI;
import com.example.ap2_ex3.database.AppDB;
import com.example.ap2_ex3.database.MessageDao;
import com.example.ap2_ex3.database.UserDao;
import com.example.ap2_ex3.entities.Message;

import java.util.List;

public class MessageRepo {
    // Singleton instance
    private static MessageRepo instance;

    // Dao fields
    private UserDao userDao;
    private MessageDao messageDao;

    // API fields
    private MessageAPI messageAPI;

    // Live Data fields
    private LiveData<List<Message>> messages;
    private MutableLiveData<Integer> status;
    private String token;

    private MessageRepo(Application application) {
        // Database initialization
        AppDB db = AppDB.getInstance(application);
        userDao = db.userDao();
        messageDao = db.messageDao();

        // API initialization
        messageAPI = new MessageAPI(messageDao, application);

        // Live Data initialization
        messages = messageDao.getMessages();
        status = new MutableLiveData<>();
    }

    // set retrofit base url in settings
    public void setMsgUrl(Application application){
        messageAPI.setMsgUrl(application);
    }

    public static synchronized MessageRepo getInstance(Application application) {
        if (instance == null) {
            instance = new MessageRepo(application);
        }
        return instance;
    }

    // Live Data listeners
    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Message dao operations
    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public List<Message> getMessagesByChat(Integer id) {
        return messageDao.getMsgByChat(id);
    }

    public LiveData<Message> getMessage(int id) {
        return messageDao.getMessage(id);
    }
  
      public void deleteAllMessages(){
        messageDao.deleteAllMessages();
    }

    // API operations
    public void getMessagesRequest(Integer chatId) {
        messageAPI.getMessages(chatId, status, token);
    }

    public void createMessageRequest(Integer chatId, String msgContent) {
        messageAPI.createMessage(chatId, msgContent, token, status);
    }
}
