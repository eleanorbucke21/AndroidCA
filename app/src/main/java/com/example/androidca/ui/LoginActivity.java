package com.example.androidca.ui;

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

            // Handle login logic
            Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show();
        });

        registerText.setOnClickListener(v -> {
            // Handle registration navigation logic
            Toast.makeText(this, "Go to Register Activity clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
