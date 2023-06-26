package com.example.ap2_ex3.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api.LoginRequest;
import com.example.ap2_ex3.viewmodel.ViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private boolean isLoggedIn = false;
    private ViewModel userModel;
    private TextInputLayout usernameView;
    private TextInputLayout passwordView;
    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.userModel = new ViewModelProvider(this).get(ViewModel.class);

        SharedPreferences sharedPref = getApplication().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean nightMode = sharedPref.getBoolean("night", false);
        if (!nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        setContentView(R.layout.activity_login_screen);

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
            Log.d("Login", "Logged in: " + liveUser.getUsername());
            isLoggedIn = true; // Set the login status to true
            if (isCurrentActivity(MainActivity.this)) {
                navigateToChatsActivity();
            }
        });
    }

    private void setTheme() {
    }

    public static void showAlert(String msg, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(msg)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void navigateToChatsActivity() {
        Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Optional: finish the current activity to prevent going back to it
    }

    private boolean isCurrentActivity(Activity activity) {
        return activity.getClass().equals(MainActivity.class);
    }

    private void navigateToSettingsActivity() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the user is logged in and navigate accordingly
        if (isLoggedIn) {
            navigateToChatsActivity();
        }
    }


}