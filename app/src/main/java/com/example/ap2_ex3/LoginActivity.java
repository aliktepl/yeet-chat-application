package com.example.ap2_ex3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout usernameTextInputLayout;
    TextInputLayout passwordTextInputLayout;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!validateUsername() || !validatePassword()) {
                // Inside your onClick() method
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Invalid username")
                        .setMessage("The username you entered is not valid.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                    return;
//                }
//                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//                startActivity(intent);
            }
        });

        TextView loginLinkTextView = findViewById(R.id.loginLink);
        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private void showInvalidUsernameMessage() {
        Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
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
        } else {
            passwordTextInputLayout.setError(null);
            passwordTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }
}