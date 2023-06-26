package com.example.ap2_ex3.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.adapters.ChatsListAdapter;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.services.MyFirebaseMessagingService;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.MessageModel;
import com.example.ap2_ex3.view_models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class ChatsActivity extends AppCompatActivity {
    private ChatModel chatModel;
    private UserModel userModel;

    private MessageModel messageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ChatsActivity.this, AddChatActivity.class);
            startActivity(intent);
        });

        MyFirebaseMessagingService firebaseMessagingService = new MyFirebaseMessagingService(chatModel);

        RecyclerView lstChats = findViewById(R.id.lstChats);
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        userModel = new ViewModelProvider(this).get(UserModel.class);
        userModel.getUser().observe(this, user -> {
            if(user != null){
                chatModel = new ViewModelProvider(this).get(ChatModel.class);
                chatModel.getChats(user);
                chatModel.observeChats().observe(this, adapter::setChats);
            }
        });

        adapter.setOnItemClickListener(new ChatsListAdapter.OnItemClickListener() {
            @Override
            public void noItemClick(Chat chat) {
                Intent intent = new Intent(ChatsActivity.this, ChatsActivity.class);
                intent.putExtra("username", chat.getRecipient());
                intent.putExtra("picture", chat.getRecipientProfPic());
                startActivity(intent);
            }
        });
    }
}