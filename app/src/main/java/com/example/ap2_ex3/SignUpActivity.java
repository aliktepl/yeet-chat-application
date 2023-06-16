package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout usernameTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    TextInputLayout confirmPasswordTextInputLayout;
    TextInputLayout displayNameTextInputLayout;

    Button pictureBtn;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        displayNameTextInputLayout = findViewById(R.id.displayNameTextInputLayout);

        pictureBtn = findViewById(R.id.pictureBtn);
        signupBtn = findViewById(R.id.signupBtn);

        TextView signupLinkTextView = findViewById(R.id.signupLink);
        signupLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    private Boolean validateUsername() {
        String username = Objects.requireNonNull(usernameTextInputLayout.getEditText()).getText().toString();
        if (username.isEmpty()) {
            usernameTextInputLayout.setError("Field cannot be empty");
            return false;
        } else if (username.contains(" ")) {
            usernameTextInputLayout.setError("White Spaces are not allowed");
            return false;
        } else if (username.length() < 4) {
            usernameTextInputLayout.setError("Username must have at least 4 characters");
            return false;
        } else if (username.length() >= 15) {
            usernameTextInputLayout.setError("Username too long");
            return false;
        } else {
            usernameTextInputLayout.setError(null);
            usernameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = Objects.requireNonNull(passwordTextInputLayout.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(confirmPasswordTextInputLayout.getEditText()).getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[!@#$%^&+=])" +   //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (password.isEmpty()) {
            passwordTextInputLayout.setError("Field cannot be empty");
            return false;
        } else if (!password.matches(passwordVal)) {
            passwordTextInputLayout.setError("Password is too weak");
            return false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordTextInputLayout.setError("Passwords do not match");
            return false;
        } else {
            passwordTextInputLayout.setError(null);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmPasswordTextInputLayout.setError(null);
            confirmPasswordTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDisplayName() {
        String displayName = Objects.requireNonNull(displayNameTextInputLayout.getEditText()).getText().toString();

        if (displayName.isEmpty()) {
            passwordTextInputLayout.setError("Field cannot be empty");
            return false;
        } else {
            return true;
        }
    }

    public void registerUser(View view) {
        if (!validateUsername() || !validatePassword() || !validateDisplayName()) {
            return;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
