package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.Users.ViewModel;
import com.example.ap2_ex3.Users.ViewModelFactory;


public class SignUpActivity extends AppCompatActivity {

    private ViewModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        userModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(ViewModel.class);
        Log.d("UserList", "onCreate called"); // Add this line to check if onCreate is called

        userModel.observeCreated().observe(this, isCreated -> {
            if(!isCreated){
                // user wasn't created!
                Log.d("Sign Up", "User Creation failed.");
            }
        });


        Button btnSignUp = findViewById(R.id.signUpBtn);
        btnSignUp.setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.username);
            EditText passwordEditText = findViewById(R.id.password);
            EditText displayNameEditText = findViewById(R.id.displayName);

            CreateUserRequest newUser = new CreateUserRequest(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    displayNameEditText.getText().toString(),
                    "0");
            userModel.createUser(newUser);
            Intent loginActivity = new Intent(this, MainActivity.class);
            startActivity(loginActivity);
        });

    }
}