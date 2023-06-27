package com.example.ap2_ex3.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ap2_ex3.R;

public class SettingsActivity extends AppCompatActivity {
    Switch switcher;
    boolean nightMode;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    private EditText serverAddressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // user entered address
        serverAddressEditText = findViewById(R.id.serverAddressEditText);
        // open sharedSettings
        sharedPreference = getApplication().getSharedPreferences
                (getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        // get editor for sharedSettings
        editor = sharedPreference.edit();

//        ImageButton backButton = findViewById(R.id.backButtonsettings);
//        backButton.setOnClickListener(v -> {
//            finish(); // Go back to the previous screen (ChatsActivity)
//        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated server address entered by the user
                String updatedServerAddress = serverAddressEditText.getText().toString();
                String url = sharedPreference.getString("address", "");
                String regexPattern = "\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b";
                String modifiedUrl = url.replaceAll(regexPattern, updatedServerAddress);
                // Save the updated server address to shared preferences
                editor.putString("address", modifiedUrl);
                editor.apply();

        switcher = findViewById(R.id.modeSwitch);


                // Show a message or perform any necessary actions after saving the server address

                // Finish the activity to go back to the previous screen
                finish();
            }
        });

        switcher = findViewById(R.id.modeSwitch);

        nightMode = sharedPreference.getBoolean("night", false);

        if (nightMode) {
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreference.edit();
                    editor.putBoolean("night", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreference.edit();
                    editor.putBoolean("night", false);
                }
                editor.apply();
            }
        });

    }
}

