package com.example.ap2_ex3.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
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
import com.example.ap2_ex3.entities.User;
import com.example.ap2_ex3.view_models.UserModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private UserModel userModel;
    private TextInputLayout usernameView;
    private TextInputLayout passwordView;
    private LoginRequest loginRequest;

    @Override
    protected void onResume() {
        super.onResume();
        userModel.setUserUrl(getApplication());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // write ip address settings to shared pref
        SharedPreferences sharedMode = getApplication().getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedMode.edit();
        if(sharedMode.getString("address", "").equals("")){
            editor2.putString("address", getString(R.string.BaseUrl));
            editor2.apply();
        }

        userModel = new ViewModelProvider(this).get(UserModel.class);

        boolean nightMode = sharedMode.getBoolean("night", false);
        if (!nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermissions();
        }

        TextView signUpLink = findViewById(R.id.loginLink);
        TextView settingsLink = findViewById(R.id.settingsLink);

        settingsLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

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
        });

        User currUser = userModel.getUserObject();
        if (currUser != null) {
            Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
            intent.putExtra("username", currUser.getUsername());
            intent.putExtra("needUser", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }


        userModel.observeToken().observe(this, liveToken -> {
            if (liveToken != null) {
                SharedPreferences sharedToken = getApplication().getSharedPreferences
                        (getString(R.string.utilities_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedToken.edit();
                editor.putString("token", liveToken);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, ChatsActivity.class);
                intent.putExtra("username", loginRequest.getUsername());
                intent.putExtra("needUser", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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
}
