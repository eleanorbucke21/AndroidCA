package com.example.androidca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.androidca.bag.BagActivity;
import com.example.androidca.categories.CategoriesActivity;
import com.example.androidca.data.DatabaseHelper;
import com.example.androidca.products.ProductsActivity;
import com.example.androidca.ui.InfoActivity;
import com.example.androidca.ui.LoginActivity;
import com.example.androidca.ui.ProfileActivity;
import com.example.androidca.ui.AdminProfileActivity;
import com.example.androidca.ui.LogoutActivity;
import com.example.androidca.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;
    private ImageButton icProfile;
    private ImageButton signInButton;
    private ImageButton logoutButton;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disable default title

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);

        // Initialize DatabaseHelper and access the database
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // This ensures the database is created if it doesn't exist

        // Set up Profile ImageButton, Sign-In Button, and Logout Button
        icProfile = findViewById(R.id.ic_profile);
        signInButton = findViewById(R.id.sign_in_button);
        logoutButton = findViewById(R.id.logout_button);

        // Check the user's sign-in status and set button visibility
        updateButtonVisibility();

        icProfile.setOnClickListener(v -> {
            String username = sharedPreferences.getString(Constants.KEY_USERNAME, null);
            String userRole = sharedPreferences.getString(Constants.KEY_USER_ROLE, null);
            Log.d(TAG, "Profile icon clicked, username: " + username + ", role: " + userRole);
            if (userRole != null && userRole.equals("admin")) {
                Intent intent = new Intent(this, AdminProfileActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(v -> {
            Log.d(TAG, "Logout button clicked");
            Intent intent = new Intent(this, LogoutActivity.class);
            startActivity(intent);
        });

        signInButton.setOnClickListener(v -> {
            Log.d(TAG, "Sign-In button clicked");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // Set up BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::handleNavigation);

        // Apply window insets for status and navigation bars
        applyWindowInsets();
    }

    private boolean isUserSignedIn() {
        boolean signedIn = sharedPreferences.getBoolean(Constants.KEY_IS_SIGNED_IN, false);
        String username = sharedPreferences.getString(Constants.KEY_USERNAME, "N/A");
        String userRole = sharedPreferences.getString(Constants.KEY_USER_ROLE, "N/A");
        Log.d(TAG, "isUserSignedIn: " + signedIn);
        Log.d(TAG, "username: " + username);
        Log.d(TAG, "userRole: " + userRole);
        return signedIn;
    }

    private void updateButtonVisibility() {
        boolean isSignedIn = isUserSignedIn();
        Log.d(TAG, "updateButtonVisibility: isSignedIn = " + isSignedIn);

        if (isSignedIn) {
            Log.d(TAG, "User is signed in");
            icProfile.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "User is not signed in");
            icProfile.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }

        // Force a re-draw of the toolbar to update the visibility of the buttons
        findViewById(R.id.toolbar).invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        updateButtonVisibility();
        resetBottomNavigationSelection();
    }

    protected void resetBottomNavigationSelection() {
        Log.d(TAG, "resetBottomNavigationSelection called");
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
    }

    protected boolean handleNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d(TAG, "Navigation item selected: " + itemId);
        if (itemId == R.id.navigation_home) {
            Log.d(TAG, "Home selected");
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        } else if (itemId == R.id.navigation_search) {
            Log.d(TAG, "Categories selected");
            startActivity(new Intent(this, CategoriesActivity.class));
            return true;
        } else if (itemId == R.id.navigation_cart) {
            Log.d(TAG, "Bag selected");
            startActivity(new Intent(this, BagActivity.class));
            return true;
        } else if (itemId == R.id.navigation_shop) {
            Log.d(TAG, "Products selected");
            startActivity(new Intent(this, ProductsActivity.class));
            return true;
        } else if (itemId == R.id.navigation_info) {
            Log.d(TAG, "Info selected");
            startActivity(new Intent(this, InfoActivity.class));
            return true;
        }
        return false;
    }

    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(false);
            windowInsetsController.setAppearanceLightNavigationBars(false);
        }
    }

    protected SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
