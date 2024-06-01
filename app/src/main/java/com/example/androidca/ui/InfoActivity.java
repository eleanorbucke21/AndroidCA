package com.example.androidca.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidca.BaseActivity;
import com.example.androidca.R;

public class InfoActivity extends BaseActivity {

    private Button btnContactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_info, findViewById(R.id.content_frame));

        btnContactUs = findViewById(R.id.btnContactUs);
        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });
    }
}
