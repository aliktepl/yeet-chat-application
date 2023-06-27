package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.view_models.ChatModel;

public class AddChatActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "" +
            "com.example.ap2_ex3.EXTRA_CONTACT";
    private EditText etAddContact;
    private ChatModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        // set token via shared preference
        chatModel = new ViewModelProvider(this).get(ChatModel.class);
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.utilities_file_key), Context.MODE_PRIVATE);
        chatModel.setToken(sharedPref.getString("token", "null"));
        // listen to add contact button
        etAddContact = findViewById(R.id.usernameEditText);
        Button btn = findViewById(R.id.loginBtn);
        btn.setOnClickListener(v -> {
            String displayName = etAddContact.getText().toString();
            if (displayName.trim().isEmpty()) {
                Toast.makeText(this, "Enter a valid username", Toast.LENGTH_SHORT).show();
            } else {
                chatModel.createChat(etAddContact.getText().toString());
                finish();
            }
        });
        chatModel.observeStatus().observe(this, status -> {
            if (status == 400) {
                Toast.makeText(this, "Contact doesn't exist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}