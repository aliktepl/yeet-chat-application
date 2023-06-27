package com.example.ap2_ex3.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.repositories.MessageRepo;

import java.util.List;


public class MessageModel extends AndroidViewModel {

    private final MessageRepo mRepository;
    private LiveData<Integer> status;
    private LiveData<List<Message>> messages;

    public MessageModel(Application application){
        super(application);
        mRepository = MessageRepo.getInstance(application);
        status = mRepository.getStatus();
        messages = mRepository.getMessages();
    }

    // Live Data listeners
    public LiveData<Integer> observeStatus() {
        return status;
    }
    public void setToken(String token) { mRepository.setToken(token); }

    // Message Dao operations
    public LiveData<List<Message>> getMessages(){return messages; }

    public LiveData<Message> getMessage(int id) { return mRepository.getMessage(id); }

    public List<Message> getMessagesByChat(Integer id) {
        return mRepository.getMessagesByChat(id);
    }

    public void deleteAllMessages() { mRepository.deleteAllMessages();}

    // Message API operations

    public void getMessagesRequest(Integer chatId) {
        mRepository.getMessagesRequest(chatId);
    }

    public void createMessageRequest(Integer chatId, String msgContent) {
        mRepository.createMessageRequest(chatId, msgContent);
    }

}
