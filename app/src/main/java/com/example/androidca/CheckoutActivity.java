package com.example.androidca;

import android.os.Bundle;

public class CheckoutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_checkout, findViewById(R.id.content_frame));

        // Any specific setup for CheckoutActivity
    }
}
