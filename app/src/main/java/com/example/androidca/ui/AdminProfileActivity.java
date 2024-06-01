package com.example.androidca.ui;

import android.os.Bundle;
import com.example.androidca.BaseActivity;
import com.example.androidca.R;

public class AdminProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the specific layout for AdminProfileActivity within the content frame
        getLayoutInflater().inflate(R.layout.activity_admin_profile, findViewById(R.id.content_frame));
    }
}
