package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ap2_ex3.Users.User;
import com.example.ap2_ex3.Users.UserViewModel;
import com.example.ap2_ex3.Users.UserViewModelFactory;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.userModel = new ViewModelProvider(this, new UserViewModelFactory(getApplication())).get(UserViewModel.class);

        TextView signUpLink = findViewById(R.id.signupLink);
        signUpLink.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        });
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            EditText usernameView = findViewById(R.id.username);
            EditText passwordView = findViewById(R.id.password);
            String username = usernameView.getText().toString();
            String password = passwordView.getText().toString();
            User queriedUser = userModel.findUser(username);
            if(queriedUser != null){
                if(Objects.equals(queriedUser.getPassword(), password)){
                    Log.d("login", "login successful");
                } else {
                    Log.d("login", "password doesn't match");
                }
            }
        });
    }
}