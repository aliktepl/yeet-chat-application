package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.Users.ViewModel;
import com.example.ap2_ex3.Users.ViewModelFactory;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ViewModel userModel;
    private String token;
    private User user;

    private LoginRequest loginRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.userModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(ViewModel.class);

        TextView signUpLink = findViewById(R.id.signupLink);
        signUpLink.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        });
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            // Token request
            EditText usernameView = findViewById(R.id.username);
            EditText passwordView = findViewById(R.id.password);
            loginRequest = new LoginRequest(usernameView.getText().toString(), passwordView.getText().toString());
            token = userModel.getToken(loginRequest).getValue();
        });

        userModel.observeToken().observe( this, liveToken -> {
            if(liveToken != null){
                user = userModel.getUser(loginRequest.getUsername(), token).getValue();
            } else {
                // invalid login
                Log.d("Login", "Invalid login");
            }
        });
        userModel.observeUser().observe(this, liveUser -> {
            Log.d("Login", "Logged in:" + liveUser.getUsername());
        });
    }
}