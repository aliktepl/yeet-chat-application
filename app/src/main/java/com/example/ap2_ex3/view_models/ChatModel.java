package com.example.ap2_ex3.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.activities.Username;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.repositories.ChatRepo;

import java.util.List;


public class ChatModel extends AndroidViewModel {

    private final ChatRepo mRepository;
    private LiveData<Integer> status;
    private LiveData<List<Chat>> chats;

    public ChatModel(Application application){
        super(application);
        mRepository = new ChatRepo(application);
        status = mRepository.getStatus();
        chats = mRepository.getAllChats();
    }

    public void setToken(String token){
        mRepository.setToken(token);
    }

    // Live Data listeners
    public LiveData<Integer> observeStatus() {
        return status;
    }

    public LiveData<List<Chat>> observeChats() {
        return chats;
    }

    // Chat API operations
    public void getChats(){
        mRepository.getChatsRequest();
    }

    public void createChat(String username) {
        mRepository.createChatRequest(username);
    }


    // Chat Dao operations
    public void ChatInsert(Chat chat) {
        mRepository.insert(chat);
    }

    public void ChatDelete(Chat chat) {
        mRepository.delete(chat);
    }

    public void ChatUpdate(Chat chat) {
        mRepository.update(chat);
    }

    public void updateLastMsg(Integer chatId, String lstMsgContent, String lstMsgTime){
        mRepository.updateLastMsg(chatId, lstMsgContent, lstMsgTime);
    }
}
