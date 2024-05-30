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
import com.example.androidca.data.UserDAO;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword, editTextEmail, editTextAddress;
    private Button buttonRegister;
    private UserDAO userDAO;
    private TextView registerHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
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
            String address = editTextAddress.getText().toString();

            userDAO.registerUser(username, password, email, address);
            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
