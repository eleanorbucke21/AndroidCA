package com.example.androidca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.example.androidca.data.DatabaseHelper;
import com.example.androidca.BagActivity;
import com.example.androidca.CategoriesActivity;
import com.example.androidca.ui.ContactUsActivity;
import com.example.androidca.ui.InfoActivity;
import com.example.androidca.ui.LoginActivity;
import com.example.androidca.MainActivity;
import com.example.androidca.ProductsActivity;
import com.example.androidca.ui.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;
    private ImageButton icProfile;
    private Button signInButton;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_IS_SIGNED_IN = "is_signed_in";
    private static final String KEY_USERNAME = "username";
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disable default title

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize DatabaseHelper and access the database
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // This ensures the database is created if it doesn't exist

        // Set up Profile ImageButton and Sign-In Button
        icProfile = findViewById(R.id.ic_profile);
        signInButton = findViewById(R.id.sign_in_button);

        if (isUserSignedIn()) {
            icProfile.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
            icProfile.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProfileActivity.class);
                String username = sharedPreferences.getString(KEY_USERNAME, null);
                if (username != null) {
                    intent.putExtra("username", username);
                }
                startActivity(intent);
            });
        } else {
            icProfile.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
            signInButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            });
        }

        // Set up BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::handleNavigation);

        // Apply window insets for status and navigation bars
        applyWindowInsets();
    }

    private boolean isUserSignedIn() {
        return sharedPreferences.getBoolean(KEY_IS_SIGNED_IN, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetBottomNavigationSelection();
    }

    protected void resetBottomNavigationSelection() {
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);
    }

    protected boolean handleNavigation(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            Log.d("BaseActivity", "Home selected");
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        } else if (itemId == R.id.navigation_search) {
            Log.d("BaseActivity", "Categories selected");
            startActivity(new Intent(this, CategoriesActivity.class));
            return true;
        } else if (itemId == R.id.navigation_cart) {
            Log.d("BaseActivity", "Bag selected");
            startActivity(new Intent(this, BagActivity.class));
            return true;
        } else if (itemId == R.id.navigation_shop) {
            Log.d("BaseActivity", "Products selected");
            startActivity(new Intent(this, ProductsActivity.class));
            return true;
        } else if (itemId == R.id.navigation_info) {
            Log.d("BaseActivity", "Info selected");
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
