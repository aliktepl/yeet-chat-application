package com.example.ap2_ex3.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

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

        serverAddressEditText = findViewById(R.id.serverAddressEditText);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String currentServerAddress = sharedPref.getString("server_address", "");
        serverAddressEditText.setText(currentServerAddress);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated server address entered by the user
                String updatedServerAddress = serverAddressEditText.getText().toString();

                // Save the updated server address to shared preferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("server_address", updatedServerAddress);
                editor.apply();

                // Show a message or perform any necessary actions after saving the server address

                // Finish the activity to go back to the previous screen
                finish();
            }
        });

        switcher = findViewById(R.id.modeSwitch);

        sharedPreference = getApplication().getSharedPreferences
                (getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        nightMode = sharedPreference.getBoolean("night", false);

        if (nightMode) {
            switcher.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreference.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreference.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
                nightMode = !nightMode;
            }
        });
    }
}

