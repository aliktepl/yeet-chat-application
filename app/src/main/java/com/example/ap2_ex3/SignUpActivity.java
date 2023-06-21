package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.ap2_ex3.api.CreateUserRequest;
import com.example.ap2_ex3.ViewModel.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class SignUpActivity extends AppCompatActivity {

    private ViewModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        this.userModel = new ViewModelProvider(this).get(ViewModel.class);
        Log.d("UserList", "onCreate called"); // Add this line to check if onCreate is called

        userModel.observeCreated().observe(this, status -> {
            if(status == 409){
                // user wasn't created!
                Log.d("Sign Up", "Duplicate user");
            } else if(status == 200){
                Log.d("Sign Up", "Sign up successful");
            } else {
                Log.d("Sign up", "Sign up Unsuccessful");
            }
        });


        Button btnSignUp = findViewById(R.id.signUpBtn);
        btnSignUp.setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.username);
            EditText passwordEditText = findViewById(R.id.password);
            EditText displayNameEditText = findViewById(R.id.displayName);
            File fname = new File("C:\\Users\\royva\\AndroidStudioProjects\\AP2-EX3\\app\\src\\main\\res\\drawable\\bowser.png");
            Bitmap bitmap = BitmapFactory.decodeFile(fname.getAbsolutePath()); // Replace imagePath with the actual path to your image file
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);

            CreateUserRequest newUser = new CreateUserRequest(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString(),
                    displayNameEditText.getText().toString(),
                    base64Image);
            userModel.createUser(newUser);
            Intent loginActivity = new Intent(this, MainActivity.class);
            startActivity(loginActivity);
        });

    }
}