package com.example.ap2_ex3.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap2_ex3.activities.Username;
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


public class ChatRepo {

    // Dao fields
    private ChatDao chatDao;

    private UserDao userDao;

    private MessageDao messageDao;

    // API fields
    private ChatAPI chatAPI;

    // Live Data fields
    private LiveData<List<Chat>> allChats;
    private MutableLiveData<Integer> status;

    private String token;


    public ChatRepo(Application application) {
        // database init
        AppDB db = AppDB.getInstance(application);
        userDao = db.userDao();
        messageDao = db.messageDao();
        chatDao = db.chatDao();
        // api init
        chatAPI = new ChatAPI(chatDao);
        // live data init
        allChats = chatDao.getAllChats();
        status = new MutableLiveData<>();
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Chat API operations
    public void getChatsRequest(){
        chatAPI.getChats(token);
    }

    public void createChatRequest(String username){
        chatAPI.createChat(token, status, username);
    }

    // Chat dao operations
    public void insert(Chat chat) {
        new Thread(() -> { chatDao.Insert(chat);}).start();
    }
    public void update(Chat chat) {
        new Thread(() -> { chatDao.Update(chat);}).start();
    }
    public void delete(Chat chat) {
        new Thread(() -> { chatDao.Delete(chat);}).start();
    }
    public void deleteAllChats(){
        new Thread(()->{chatDao.deleteAllChats();}).start();
    }

    public void updateLastMsg(Integer chatId, String lstMsgContent, String lstMsgTime){
        new Thread(() -> {
            chatDao.updateLastMessage(chatId, lstMsgContent, lstMsgTime);
        }).start();
    }

    // Live Data listeners
    public MutableLiveData<Integer> getStatus() {
        return status;
    }
    public LiveData<List<Chat>> getAllChats() {
        return allChats;
    }

}
