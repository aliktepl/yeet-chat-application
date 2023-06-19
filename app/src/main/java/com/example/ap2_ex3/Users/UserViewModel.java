package com.example.ap2_ex3.Users;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {

    private UsersRepository mRepository;

    public UserViewModel(Application application){
        mRepository = new UsersRepository(application);
    }

    public LiveData<List<User>> getUsers() { return mRepository.getAll(); }

    public void add(User user) { mRepository.add(user); }

    public User findUser(String username){ return mRepository.findUser(username); }

//    public void delete(User user) { mRepository.delete(user); }
//
//    public void reload() { mRepository.reload(); }

}
