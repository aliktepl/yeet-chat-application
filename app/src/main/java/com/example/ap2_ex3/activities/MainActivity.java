package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.viewmodel.ViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ViewModel userModel;
    private TextInputLayout usernameView;
    private TextInputLayout passwordView;
    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        this.userModel = new ViewModelProvider(this).get(ViewModel.class);

        checkPermissions();

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

        });
        userModel.observeStatus().observe(this, status -> {
            if (status == 404) {
                MainActivity.showAlert("Invalid username or password", this);
            }
        });
        userModel.observeToken().observe(this, liveToken -> {
            if (liveToken != null) {
                userModel.getUser(loginRequest.getUsername(), liveToken);
            } else {
                // invalid login
                Log.d("Login", "Request failed");
            }
        });
        userModel.observeUser().observe(this, liveUser -> {
            Log.d("Login", "Logged in:" + liveUser.getUsername());
            Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
            startActivity(intent);
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

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
}