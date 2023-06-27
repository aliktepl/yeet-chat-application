package com.example.ap2_ex3.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.api_requests.LoginRequest;
import com.example.ap2_ex3.view_models.ChatModel;
import com.example.ap2_ex3.view_models.MessageModel;
import com.example.ap2_ex3.view_models.UserModel;
import com.google.android.material.textfield.TextInputLayout;


import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private UserModel userModel;
    private TextInputLayout usernameView;
    private TextInputLayout passwordView;
    private LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
    }


    public static void showAlert(String msg, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(msg)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void navigateToChatsActivity() {
        Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
        startActivity(intent);
    }

    private boolean isCurrentActivity(Activity activity) {
        return activity.getClass().equals(MainActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeActivity();
    }

    private void initializeActivity(){
        setContentView(R.layout.activity_login_screen);
        userModel = new ViewModelProvider(this).get(UserModel.class);

        SharedPreferences sharedMode = getApplication().getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        boolean nightMode = sharedMode.getBoolean("night", false);
        if (!nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermissions();
        }

        userModel.getUser().observe(this, user -> {
            if (user != null) {
                Log.d("Login", "User inserted to db and login was successful");
                if (isCurrentActivity(MainActivity.this)) {
                    checkActivityStack();
                    navigateToChatsActivity();
                }
            }
        });

        TextView signUpLink = findViewById(R.id.loginLink);
        signUpLink.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
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
                    SharedPreferences sharedToken = getApplication().getSharedPreferences
                            (getString(R.string.utilities_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedToken.edit();
                    editor.putString("token", liveToken);
                    editor.apply();
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

        });
    }
    private void checkActivityStack() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            // Get the list of running tasks (activities) in the activity stack
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(10);

            // Iterate through the task list and print information about each activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                Log.d("Activity Stack", "Activity: " + taskInfo.topActivity.getClassName());
            }
        }
    }
}
