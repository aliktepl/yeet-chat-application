package com.example.ap2_ex3.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ap2_ex3.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String NIGHT_MODE_KEY = "NightMode";

    private Switch modeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        modeSwitch = findViewById(R.id.modeSwitch);

        // Set the initial state of the switch based on the saved preference
        boolean isNightMode = getNightModeFromPreference();
        modeSwitch.setChecked(isNightMode);

        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the new state to the preference
                saveNightModeToPreference(isChecked);

                // Apply the new theme based on the switch state
                applyTheme(isChecked);
            }
        });
    }

    private boolean getNightModeFromPreference() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return preferences.getBoolean(NIGHT_MODE_KEY, false);
    }

    private void saveNightModeToPreference(boolean isNightMode) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(NIGHT_MODE_KEY, isNightMode);
        editor.apply();
    }

    private void applyTheme(boolean isNightMode) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Restart the activity to apply the new theme
        recreate();
    }
}
