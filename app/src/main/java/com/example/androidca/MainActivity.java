package com.example.androidca;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;

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
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
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
