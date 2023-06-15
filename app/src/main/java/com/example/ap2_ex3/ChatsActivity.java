package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.ap2_ex3.adapters.ChatsListAdapter;
import com.example.ap2_ex3.entities.Chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        RecyclerView lstChats = findViewById(R.id.lstChats);
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1, R.drawable.bowser, "Alik", "Alik1","this is a test with a very long string, so long that it goes off the screen", new Date()));
        adapter.setChats(chats);

    }
}