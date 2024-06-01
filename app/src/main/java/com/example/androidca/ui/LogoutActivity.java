package com.example.androidca.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.androidca.BaseActivity;
import com.example.androidca.R;

public class LogoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout); // Ensure you have a layout file for this activity
        logout();
    }

    private void logout() {
        // Clear session data
        clearSessionData();

        // Navigate to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Finish current activity to prevent returning back
    }

    private void clearSessionData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
}
