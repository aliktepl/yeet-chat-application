package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.adapters.MessageListAdapter;
import com.example.ap2_ex3.entities.Message;
import com.example.ap2_ex3.services.MyFirebaseMessagingService;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private MessageModel messageViewModel;

    private ChatModel chatModel;
    private TextView contactName;
    private ImageView contactImage;
    private EditText messageInput;
    private ImageButton sendButton;
    private String token;
    private Integer chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageViewModel = new ViewModelProvider(this).get(MessageModel.class);
        chatModel = new ViewModelProvider(this).get(ChatModel.class);
        Bundle bundle = getIntent().getExtras();

        new MyFirebaseMessagingService();

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
            String message = messageInput.getText().toString().trim(); // Trim any leading or trailing whitespace

            if (!message.isEmpty()) { // Check if the message is not empty
                messageViewModel.createMessageRequest(chatId, message);
                messageInput.setText(""); // Clear the text from the input field
            }
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


        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshLayoutMsg);
        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        final MessageListAdapter adapter = new MessageListAdapter(this, currentUser);
        lstMessages.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        lstMessages.setLayoutManager(manager);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                lstMessages.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            messageViewModel.getMessages();
            swipeRefreshLayout.setRefreshing(false);
        });

        messageViewModel.getMessages().observe(this, messages -> {
            if (!messages.isEmpty()) {
                List<Message> renderMsg = new ArrayList<>();
                for (Message msg : messages) {
                    if (msg.getChatId() == chatId) {
                        renderMsg.add(msg);
                    }
                }
                if (!renderMsg.isEmpty()) {
                    adapter.setMessages(renderMsg);
                    // update last msg in chat
                    Message lstMsg = renderMsg.get(renderMsg.size() - 1);
                    chatModel.updateLastMsg(chatId,lstMsg.getContent(), lstMsg.getCreated());
                }
            }
        });
    }
}
