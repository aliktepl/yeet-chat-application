package com.example.ap2_ex3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.adapters.ChatsListAdapter;
import com.example.ap2_ex3.entities.Chat;
import com.example.ap2_ex3.services.MyFirebaseMessagingService;
import com.example.ap2_ex3.viewmodel.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;

public class ChatsActivity extends AppCompatActivity {
    private ViewModel chatsViewModel;
    public static final int ADD_CONTACT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ChatsActivity.this, AddChatActivity.class);
            startActivityForResult(intent, ADD_CONTACT_REQUEST);
        });

        MyFirebaseMessagingService firebaseMessagingService = new MyFirebaseMessagingService(chatsViewModel);

        RecyclerView lstChats = findViewById(R.id.lstChats);
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        chatsViewModel = new ViewModelProvider(this).get(ViewModel.class);
        chatsViewModel.getChats().observe(this, adapter::setChats);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(AddChatActivity.EXTRA_CONTACT);

            Chat chat = new Chat(R.drawable.bowser, name, name, "test", new Date());
            chatsViewModel.Insert(chat);
        } else {
            Toast.makeText(this, "Contact not added", Toast.LENGTH_SHORT).show();
        }
    }
}