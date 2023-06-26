package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.ap2_ex3.viewmodel.ViewModel;

public class ChatActivity extends AppCompatActivity {
    private static final int MENU_SETTINGS = R.id.menu_settings;

    private ViewModel messageViewModel;
    private TextView contactName;
    private ImageView contactImage;

    private EditText messageInput;

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();

        String username = bundle.getString("username");
        String picture = bundle.getString("picture");

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            messageInput = findViewById(R.id.messageInput);
            //TODO: Message message = new Message("message text", "sender");
            //TODO: messageViewModel.Insert(message);
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
        final MessageListAdapter adapter = new MessageListAdapter(this, username);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        messageViewModel = new ViewModelProvider(this).get(ViewModel.class);
        messageViewModel.getMessages().observe(this, adapter::setMessages);

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
