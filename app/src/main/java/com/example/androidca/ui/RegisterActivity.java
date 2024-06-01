package com.example.androidca.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidca.BaseActivity;
import com.example.androidca.R;
import com.example.androidca.data.UserDAO;

public class RegisterActivity extends BaseActivity {
    private EditText editTextUsername, editTextPassword, editTextEmail;
    private Button buttonRegister;
    private UserDAO userDAO;
    private TextView registerHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_register, findViewById(R.id.content_frame));

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonRegister = findViewById(R.id.buttonRegister);
        registerHeader = findViewById(R.id.registerHeader);

        // Underline the register header text programmatically
        SpannableString registerText = new SpannableString(getString(R.string.register));
        registerText.setSpan(new UnderlineSpan(), 0, registerText.length(), 0);
        registerHeader.setText(registerText);

        userDAO = new UserDAO(this);

        buttonRegister.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String email = editTextEmail.getText().toString();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            userDAO.registerUser(username, password, email);
            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
