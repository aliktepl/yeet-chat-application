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
        mRepository = new MessageRepo(application);
        status = mRepository.getStatus();
        messages = mRepository.getMessages();
    }

    public LiveData<Integer> observeStatus() {
        return status;
    }

    // Message Dao operations
    public LiveData<List<Message>> getMessages(){return messages; }

    public LiveData<Message> getMessage(int id) { return mRepository.getMessage(id); }

}
