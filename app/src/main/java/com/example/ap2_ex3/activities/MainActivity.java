package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.view_models.UserModel;
import com.example.ap2_ex3.entities.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private UserModel userModel;
    private TextInputLayout usernameView;
    private TextInputLayout passwordView;
    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.userModel = new ViewModelProvider(this).get(UserModel.class);

        TextView signUpLink = findViewById(R.id.loginLink);
        signUpLink.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        });
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            // Token request
            usernameView = findViewById(R.id.usernameTextInputLayout);
            passwordView = findViewById(R.id.passwordTextInputLayout);
            loginRequest = new LoginRequest(Objects.requireNonNull(usernameView.getEditText()).getText().toString(),
                    Objects.requireNonNull(passwordView.getEditText()).getText().toString());
            userModel.getToken(loginRequest);

            userModel.observeToken().observe(this, liveToken -> {
                if (liveToken != null) {
                    userModel.getCurrUser(loginRequest.getUsername(), liveToken);
                } else {
                    // invalid login
                    Log.d("Login", "Request failed");
                }
            });

            userModel.observeStatus().observe(this, status -> {
                if (status == 404) {
                    MainActivity.showAlert("Invalid username or password", this);
                }
            });

            userModel.observeStatus().observe(this, status -> {
                if(status == 1) {
                    Log.d("Login", "User inserted to db and login was successful");
                    Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
                    startActivity(intent);
                }
            });

        });

    }


    public static void showAlert(String msg, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(msg)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


