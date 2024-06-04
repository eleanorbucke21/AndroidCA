package com.example.androidca.information;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.androidca.BaseActivity;
import com.example.androidca.R;

public class ContactUsActivity extends BaseActivity {

    private EditText etName, etEmail, etMessage;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_contact_us, findViewById(R.id.content_frame));

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMessage = findViewById(R.id.etMessage);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String message = etMessage.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Toast.makeText(ContactUsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Handle message submission (e.g., send to server or save locally)
                submitMessage(name, email, message);
            }
        });
    }

    private void submitMessage(String name, String email, String message) {
        // Example: Save message to the database or send to server
        Toast.makeText(this, "Thank you for your message!", Toast.LENGTH_SHORT).show();
        // Finish the activity
        finish();
    }
}
