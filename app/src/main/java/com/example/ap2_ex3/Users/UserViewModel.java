package com.example.ap2_ex3.Users;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {

    private UsersRepository mRepository;
    private LiveData<List<User>> users;

    public UserViewModel(Context context){
        mRepository = new UsersRepository(context);
        users = mRepository.getAll();
    }

    public LiveData<List<User>> getUsers() { return users; }

    public void add(User user) { mRepository.add(user); }

    public User findUser(String username){ return mRepository.findUser(username); }

//    public void delete(User user) { mRepository.delete(user); }
//
//    public void reload() { mRepository.reload(); }

}
