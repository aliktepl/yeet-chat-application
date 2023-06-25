package com.example.ap2_ex3.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.adapters.ChatsListAdapter;
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.view_models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChatsActivity extends AppCompatActivity {
    private ChatModel chatModel;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ChatsActivity.this, AddChatActivity.class);
            startActivity(intent);
        });

        RecyclerView lstChats = findViewById(R.id.lstChats);
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        userModel = new ViewModelProvider(this).get(UserModel.class);
        userModel.getUsers().observe(this, users -> {
            if(!users.isEmpty()){
                List<User> myUser = userModel.getMyUser();
                if(myUser != null){
                    chatModel = new ViewModelProvider(this).get(ChatModel.class);
                    chatModel.getChats(myUser.get(0));
                    chatModel.observeChats().observe(this, new Observer<List<Chat>>() {
                        @Override
                        public void onChanged(List<Chat> chats) {
                            adapter.setChats(chats);
                        }
                    });
                }
            }
        });

    }

}