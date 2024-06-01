package com.example.androidca.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidca.R;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView registerText, loginHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        registerText = findViewById(R.id.registerText);
        loginHeader = findViewById(R.id.loginHeader);

        // Underline the login header text programmatically
        SpannableString loginText = new SpannableString(getString(R.string.login));
        loginText.setSpan(new UnderlineSpan(), 0, loginText.length(), 0);
        loginHeader.setText(loginText);

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            if (isValidCredentials(username, password)) {
                if (isAdminUser(username, password)) {
                    // Redirect to AdminProfileActivity
                    Intent intent = new Intent(this, AdminProfileActivity.class);
                    startActivity(intent);
                    finish(); // Optional: finish LoginActivity so user can't return with back button
                } else {
                    // Redirect to normal user profile activity
                    Intent intent = new Intent(this, ProfileActivity.class);  // Updated to use the correct class name
                    intent.putExtra("username", username);  // Pass the username to ProfileActivity
                    startActivity(intent);
                    finish(); // Optional: finish LoginActivity
                }
            } else {
                // Handle login failure
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        registerText.setOnClickListener(v -> {
            // Handle registration navigation logic
            Toast.makeText(this, "Go to Register Activity clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean isValidCredentials(String username, String password) {
        // Here you should implement your actual credentials checking logic
        // This example just checks if the username is "admin" and the password is "Limerick"
        return "admin".equals(username) && "Limerick".equals(password);
    }

    private boolean isAdminUser(String username, String password) {
        // This function determines if the user is an admin
        // Here it's just a placeholder to match the example credentials
        return "admin".equals(username) && "Limerick".equals(password);
    }
}
