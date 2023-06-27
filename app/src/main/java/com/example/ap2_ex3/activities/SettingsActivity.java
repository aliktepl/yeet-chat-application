package com.example.ap2_ex3.activities;

import android.content.Context;
import android.content.SharedPreferences;
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

        serverAddressEditText = findViewById(R.id.serverAddressEditText);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.settings_file_key), Context.MODE_PRIVATE);
        String currentServerAddress = sharedPref.getString("server_address", "");
        serverAddressEditText.setText(currentServerAddress);

        switcher = findViewById(R.id.modeSwitch);

        sharedPreference = getApplication().getSharedPreferences
                (getString(R.string.settings_file_key), Context.MODE_PRIVATE);

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

