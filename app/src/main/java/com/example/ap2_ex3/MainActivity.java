package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        TextView signUpLink = findViewById(R.id.signupLink);
        signUpLink.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        });

    }
}