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

    public LiveData<Integer> observeStatus() {
        return status;
    }


    // Chat API operations
    public void getChats(User myUser){
        mRepository.getChatsRequest(myUser);
    }

    public void createChat(String username, User myUser) {
        mRepository.createChatRequest(username, myUser);
    }

    public LiveData<List<Chat>> observeChats() {
        return chats;
    }


    // Chat Dao operations
    public void ChatInsert(Chat chat) {
        mRepository.insert(chat);
    }

//    public void ChatDelete(Chat chat) {
//        mRepository.delete(chat);
//    }
//
//    public void ChatUpdate(Chat chat) {
//        mRepository.update(chat);
//    }
}
