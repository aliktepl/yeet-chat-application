package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Delay transition to the home screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the home screen activity
                Intent intent = new Intent(MainActivity.this, SignUpScreenActivity.class);
                startActivity(intent);
                finish(); // Optional: Remove this activity from the back stack
            }
        }, 2000); // 2-second delay
    }
}