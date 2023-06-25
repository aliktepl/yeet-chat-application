package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.UserModel;

public class AddChatActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "" +
            "com.example.ap2_ex3.EXTRA_CONTACT";
    private EditText etAddContact;
    private ChatModel chatModel;

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        userModel = new ViewModelProvider(this).get(UserModel.class);

        userModel.observeMyUser().observe(this, myUser -> {
            if(myUser != null){
                chatModel = new ViewModelProvider(this).get(ChatModel.class);
                etAddContact = findViewById(R.id.usernameEditText);
                Button btn = findViewById(R.id.loginBtn);
                btn.setOnClickListener(v -> {
                    String displayName = etAddContact.getText().toString();
                    if (displayName.trim().isEmpty()){
                        Toast.makeText(this, "Enter a valid username", Toast.LENGTH_SHORT).show();
                    } else {
                        chatModel.createChat(etAddContact.getText().toString(), myUser);
                        finish();
                    }
                });
                chatModel.observeStatus().observe(this, status -> {
                    if(status == 400){
                        Toast.makeText(this, "Contact doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}