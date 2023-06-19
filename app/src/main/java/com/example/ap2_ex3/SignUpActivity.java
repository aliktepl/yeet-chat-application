package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.ap2_ex3.Users.User;
import com.example.ap2_ex3.Users.UserViewModel;
import com.example.ap2_ex3.Users.UserViewModelFactory;


public class SignUpActivity extends AppCompatActivity {

    private UserViewModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        userModel = new ViewModelProvider(this, new UserViewModelFactory(getApplication())).get(UserViewModel.class);
        Log.d("UserList", "onCreate called"); // Add this line to check if onCreate is called

        userModel.getUsers().observe(this, userList -> {
            Log.d("UserList", "Observer triggered"); // Add this line to check if the observer is triggered
            for (User user : userList) {
                Log.d("UserList", user.getUsername());
            }
        });


        Button btnSignUp = findViewById(R.id.signUpBtn);
        btnSignUp.setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.username);
            EditText passwordEditText = findViewById(R.id.password);
            EditText displayNameEditText = findViewById(R.id.displayName);

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String displayName = displayNameEditText.getText().toString();

            User newUser = new User(username, password, displayName);
            userModel.add(newUser);
            Intent loginActivity = new Intent(this, MainActivity.class);
            startActivity(loginActivity);
        });

    }
}