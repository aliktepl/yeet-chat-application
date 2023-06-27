package com.example.ap2_ex3.activities;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.adapters.ChatsListAdapter;
import com.example.ap2_ex3.services.MyFirebaseMessagingService;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.MessageModel;
import com.example.ap2_ex3.view_models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChatsActivity extends AppCompatActivity {
    private static final int MENU_SETTINGS = R.id.menu_settings;
    private static final int LOGOUT = R.id.menu_logout;

    private TextView tvUserName;
    private ImageView ivUserProfile;

    private UserModel userModel;
    private ChatModel chatModel;
    private MessageModel messageModel;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.utilities_file_key), Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "null");

        tvUserName = findViewById(R.id.tvUserName);
        ivUserProfile = findViewById(R.id.ivUserProfile);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getBoolean("needUser")){
            userModel.getCurrUser(bundle.getString("username"), token);
        }

        userModel.getUser().observe(this, user -> {
            if(user != null) {
                String base64Image = user.getProfPic();
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivUserProfile.setImageBitmap(decodedBitmap);
                tvUserName.setText(user.getDisplayName());
            }
            });

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ChatsActivity.this, AddChatActivity.class);
            startActivity(intent);
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView lstChats = findViewById(R.id.lstChats);
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(() -> {
            chatModel.getChats();
            swipeRefreshLayout.setRefreshing(false);
        });

        chatModel = new ViewModelProvider(this).get(ChatModel.class);
        messageModel = new ViewModelProvider(this).get(MessageModel.class);

        adapter.setOnItemClickListener(chat -> {
            Intent intent = new Intent(ChatsActivity.this, ChatActivity.class);
            intent.putExtra("currentUser", tvUserName.getText().toString());
            intent.putExtra("username", chat.getRecipient());
            intent.putExtra("picture", chat.getRecipientProfPic());
            intent.putExtra("id", chat.getId());
            startActivity(intent);
        });

        chatModel = new ViewModelProvider(this).get(ChatModel.class);
        chatModel.setToken(token);
        chatModel.getChats();
        chatModel.observeChats().observe(this, chats -> {
            if(!chats.isEmpty()){
                adapter.setChats(chats);
            }
        });

        ImageButton settingsButton = findViewById(R.id.moreBtn);
        settingsButton.setOnClickListener(this::showPopupMenu);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_SETTINGS) {
                openSettings();
                return true;
            } else if (item.getItemId() == LOGOUT) {
                logout();
                return true;
            }
            return super.onOptionsItemSelected(item);
        });
        popupMenu.show();
    }

    private void openSettings() {
        Intent intent = new Intent(ChatsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void logout() {
        userModel.deleteAllUsers();
        chatModel.deleteAllChats();
        messageModel.deleteAllMessages();
        finish();
    }

}