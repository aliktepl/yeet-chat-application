package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;

import com.example.ap2_ex3.Users.UserViewModel;


public class SignUpActivity extends AppCompatActivity {

    private UserViewModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        userModel = new ViewModelProvider(this).get(UserViewModel.class);
//        userModel.getUsers().observe(this, user -> get);

        Button btnSignUp = findViewById(R.id.signUpBtn);
        btnSignUp.setOnClickListener(v -> {

        });

//        Button btnLogin = findViewById(R.id.signupBtn);
//        btnLogin.setOnClickListener(v -> {
//            Intent intent = new Intent(this, LoginScreenActivity.class);
//            startActivity(intent);
//        });

    }
}