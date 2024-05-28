package com.example.androidca;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disable default title

        // Set up BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean handled = handleNavigation(item);
                if (handled) {
                    resetBottomNavigationSelection();
                }
                return handled;
            }
        });

        // Apply window insets for status and navigation bars
        applyWindowInsets();
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
        } else {
            return false;
        }
    }

    private void applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(false);
            windowInsetsController.setAppearanceLightNavigationBars(false);
        }
    }
}
