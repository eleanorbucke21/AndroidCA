package com.example.androidca.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidca.BaseActivity;
import com.example.androidca.utils.Constants;
import com.example.androidca.R;
import com.example.androidca.data.UserDAO;
import com.example.androidca.models.User;

public class LoginActivity extends BaseActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView registerText, loginHeader;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_login, findViewById(R.id.content_frame));

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        registerText = findViewById(R.id.registerText);
        loginHeader = findViewById(R.id.loginHeader);

        // Initialize UserDAO
        userDAO = new UserDAO(this);

        // Underline the login header text programmatically
        SpannableString loginText = new SpannableString(getString(R.string.login));
        loginText.setSpan(new UnderlineSpan(), 0, loginText.length(), 0);
        loginHeader.setText(loginText);

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            Log.d("LoginActivity", "Attempting to log in with username: " + username + " and password: " + password);

            if (userDAO.loginUser(username, password)) {
                Log.d("LoginActivity", "Login successful for username: " + username);

                // Retrieve the user info to get the user ID
                User user = userDAO.getUserInfo(username);

                // Save login status, username, role, and user ID in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                editor.putString(Constants.KEY_USERNAME, username);
                editor.putInt(Constants.KEY_USER_ID, user.getId()); // Save user ID

                if (userDAO.isAdminUser(username)) {
                    editor.putString(Constants.KEY_USER_ROLE, "admin");
                    editor.apply();

                    // Redirect to AdminProfileActivity
                    Intent intent = new Intent(this, AdminProfileActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    editor.putString(Constants.KEY_USER_ROLE, "user");
                    editor.apply();

                    // Redirect to normal user profile activity
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
                finish();
            } else {
                Log.e("LoginActivity", "Invalid credentials for username: " + username);
                // Handle login failure
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        registerText.setOnClickListener(v -> {
            // Handle registration navigation logic
            Toast.makeText(this, "Go to Register Activity clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
