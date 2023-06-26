package com.example.ap2_ex3.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.view_models.UserModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import android.util.Base64;
import android.util.Log;

import com.example.ap2_ex3.api_requests.CreateUserRequest;

import java.io.ByteArrayOutputStream;

import android.content.ContentResolver;

import java.io.IOException;
import java.io.InputStream;

public class SignUpActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 1000;
    private TextInputLayout usernameTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private TextInputLayout confirmPasswordTextInputLayout;
    private TextInputLayout displayNameTextInputLayout;
    private Button pictureBtn;
    private Button signupBtn;
    private Uri imageUri;
    private String base64Img;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        this.userModel = new ViewModelProvider(this).get(UserModel.class);
        Log.d("UserList", "onCreate called"); // Add this line to check if onCreate is called

        userModel.observeStatus().observe(this, status -> {
            if (status == 409) {
                // user wasn't created!
                MainActivity.showAlert("User already exists", this);
            } else if (status == 200) {
                Log.d("Sign Up", "Sign up successful");
                Intent loginActivity = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(loginActivity);
            } else {
                MainActivity.showAlert("Sign up failed", this);
            }
        });

        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        displayNameTextInputLayout = findViewById(R.id.displayNameTextInputLayout);

        pictureBtn = findViewById(R.id.pictureBtn);
        pictureBtn.setOnClickListener(v -> handleImage());

        signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(v -> {

            if (!validateUsername() || !validatePassword() || !validateDisplayName()) {
                return;
            }
            try {
                base64Img = imageToBase64(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            CreateUserRequest newUser = new CreateUserRequest(Objects.requireNonNull(usernameTextInputLayout.getEditText()).getText().toString(),
                    Objects.requireNonNull(passwordTextInputLayout.getEditText()).getText().toString(),
                    Objects.requireNonNull(displayNameTextInputLayout.getEditText()).getText().toString(),
                    base64Img);

            userModel.createUser(newUser);
        });

        TextView signupLinkTextView = findViewById(R.id.signupLink);
        signupLinkTextView.setOnClickListener(v -> {
            Intent loginIntent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(loginIntent);
        });
    }

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (imageUri != null) {
                        String fileName = getImageFileName(imageUri);
                        pictureBtn.setText(fileName);
                    }
                }
            }
    );

    private void handleImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                pictureBtn.setText(getImageFileName(imageUri));
            }
        }
    }

    private String getImageFileName(Uri uri) {
        String fileName = null;
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            if (columnIndex != -1) {
                fileName = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }

    private Boolean validateUsername() {
        String username = Objects.requireNonNull(usernameTextInputLayout.getEditText()).getText().toString();
        if (username.isEmpty()) {
            usernameTextInputLayout.setError("Field cannot be empty");
            return false;
        } else if (username.contains(" ")) {
            usernameTextInputLayout.setError("White Spaces are not allowed");
            return false;
        } else if (username.length() < 4) {
            usernameTextInputLayout.setError("Username must have at least 4 characters");
            return false;
        } else if (username.length() >= 15) {
            usernameTextInputLayout.setError("Username too long");
            return false;
        } else {
            usernameTextInputLayout.setError(null);
            usernameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String password = Objects.requireNonNull(passwordTextInputLayout.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(confirmPasswordTextInputLayout.getEditText()).getText().toString();
        String passwordVal = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[!@#$%^&+=])" +   //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (password.isEmpty()) {
            passwordTextInputLayout.setError("Field cannot be empty");
            return false;
        } else if (!password.matches(passwordVal)) {
            passwordTextInputLayout.setError("Password is too weak");
            return false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordTextInputLayout.setError("Passwords do not match");
            return false;
        } else {
            passwordTextInputLayout.setError(null);
            passwordTextInputLayout.setErrorEnabled(false);
            confirmPasswordTextInputLayout.setError(null);
            confirmPasswordTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDisplayName() {
        String displayName = Objects.requireNonNull(displayNameTextInputLayout.getEditText()).getText().toString();

        if (displayName.isEmpty()) {
            displayNameTextInputLayout.setError("Field cannot be empty");
            return false;
        } else {
            displayNameTextInputLayout.setError(null);
            displayNameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public String imageToBase64(Uri imageUri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(imageUri);

        // Read the input stream into a byte array
        byte[] imageBytes = getBytes(inputStream);

        // Encode the byte array to Base64
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}