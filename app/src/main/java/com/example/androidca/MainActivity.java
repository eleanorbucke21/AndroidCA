package com.example.androidca;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;

public class MainActivity extends BaseActivity {

    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the specific layout for MainActivity within the content frame
        getLayoutInflater().inflate(R.layout.activity_main, findViewById(R.id.content_frame));

        // Initialize the background image
        backgroundImage = findViewById(R.id.backgroundImage);
        adjustBackgroundImage(getResources().getConfiguration().orientation);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adjustBackgroundImage(newConfig.orientation);
    }

    private void adjustBackgroundImage(int orientation) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            backgroundImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // Hide the quote container if in portrait mode
            View quoteContainer = findViewById(R.id.quoteContainer);
            if (quoteContainer != null) {
                quoteContainer.setVisibility(View.GONE);
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
            // Show the quote container if in landscape mode
            View quoteContainer = findViewById(R.id.quoteContainer);
            if (quoteContainer != null) {
                quoteContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetBottomNavigationSelection();
        // Clear the intent data to reset any filters
        getIntent().removeExtra("category");
    }
}
