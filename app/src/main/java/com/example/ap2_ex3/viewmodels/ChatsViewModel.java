package com.example.ap2_ex3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.repositories.ChatRepository;

import java.util.List;

public class ChatsViewModel extends AndroidViewModel {

    private ChatRepository repository;

    private LiveData<List<Chat>> chats;

    public ChatsViewModel(@NonNull Application application) {
        super(application);
        repository = new ChatRepository(application);
        chats = repository.getAllChats();
    }


    public LiveData<List<Chat>> getChats() {
        return chats;
    }

    public void Insert(Chat chat) {
        repository.insert(chat);
    }

    public void Delete(Chat chat) {
        repository.delete(chat);
    }

    public void Update(Chat chat) {
        repository.update(chat);
    }
}
