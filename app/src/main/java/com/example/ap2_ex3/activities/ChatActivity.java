package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.SettingsActivity;
import com.example.ap2_ex3.adapters.MessageListAdapter;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.services.MyFirebaseMessagingService;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final int MENU_SETTINGS = R.id.menu_settings;

    private MessageModel messageViewModel;

    private ChatModel chatModel;
    private TextView contactName;
    private ImageView contactImage;
    private EditText messageInput;
    private Button sendButton;

    private String token;
    private Integer chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageViewModel = new ViewModelProvider(this).get(MessageModel.class);
        chatModel = new ViewModelProvider(this).get(ChatModel.class);
        Bundle bundle = getIntent().getExtras();

        String username = bundle.getString("username");
        String picture = bundle.getString("picture");
        String currentUser = bundle.getString("currentUser");

        // init chatId and token
        chatId = bundle.getInt("id");
        SharedPreferences sharedToken = getApplication().getSharedPreferences(getString(R.string.utilities_file_key), Context.MODE_PRIVATE);
        token = sharedToken.getString("token", "null");
        messageViewModel.setToken(token);
        messageViewModel.getMessagesRequest(chatId);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            messageInput = findViewById(R.id.messageInput);
            messageViewModel.createMessageRequest(chatId, messageInput.getText().toString());
        });

        contactName = findViewById(R.id.contactName);
        contactImage = findViewById(R.id.contactImage);

        contactName.setText(username);

        byte[] decodedString = Base64.decode(picture, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        contactImage.setImageBitmap(decodedBitmap);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish(); // Go back to the previous screen (ChatsActivity)
        });

        ImageButton settingsButton = findViewById(R.id.moreBtn);
        settingsButton.setOnClickListener(this::showPopupMenu);

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        final MessageListAdapter adapter = new MessageListAdapter(this, currentUser);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        messageViewModel.getMessages().observe(this, messages -> {
            if(!messages.isEmpty()){
                List<Message> renderMsg = new ArrayList<>();
                for (Message msg : messages){
                    if(msg.getChatId() == chatId){
                        renderMsg.add(msg);
                    }
                }
                if(!renderMsg.isEmpty()){
                    adapter.setMessages(renderMsg);
                    // update last msg in chat
                    Message lstMsg = renderMsg.get(renderMsg.size() - 1);
                    chatModel.updateLastMsg(chatId,lstMsg.getContent(), lstMsg.getCreated());
                }
            }
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.more_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_SETTINGS) {
                openSettings();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void openSettings() {
        Intent intent = new Intent(ChatActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


}
