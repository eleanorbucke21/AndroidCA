package com.example.androidca;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        backgroundImage = findViewById(R.id.backgroundImage);
        adjustBackgroundImage(getResources().getConfiguration().orientation);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disable default title

        // Set status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        // Set navigation bar color
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set status bar icons to white
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(false);
            windowInsetsController.setAppearanceLightNavigationBars(false);
        }

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    Log.d("MainActivity", "Home selected");
                    // Handle Home action
                    return true;
                } else if (itemId == R.id.navigation_search) {
                    Log.d("MainActivity", "Search selected");
                    // Handle Search action
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    Log.d("MainActivity", "Cart selected");
                    // Handle Cart action
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Log.d("MainActivity", "Profile selected");
                    // Handle Profile action
                    return true;
                } else {
                    return false;
                }
            }
        });

        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setOnClickListener(v -> {
            // Navigate back to the main screen
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustBackgroundImage(newConfig.orientation);
    }

    private void adjustBackgroundImage(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            backgroundImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
