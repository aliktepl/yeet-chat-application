package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddChatActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "" +
            "com.example.ap2_ex3.EXTRA_CONTACT";
    private EditText etAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        etAddContact = findViewById(R.id.usernameEditText);
        Button btn = findViewById(R.id.loginBtn);
        btn.setOnClickListener(v -> addContact());
    }

    private void addContact() {
        String displayName = etAddContact.getText().toString();
        if (displayName.trim().isEmpty()) {
            Toast.makeText(this, "Enter a valid username", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_CONTACT, displayName);

        setResult(RESULT_OK, data);
        finish();
    }
}