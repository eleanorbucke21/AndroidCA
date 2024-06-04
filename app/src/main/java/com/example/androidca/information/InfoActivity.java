package com.example.androidca.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.Html;
import com.example.androidca.BaseActivity;
import com.example.androidca.R;

public class InfoActivity extends BaseActivity {

    private Button btnFAQ;
    private LinearLayout faqInformationSection;
    private Button btnDeliveryQuestions;
    private LinearLayout deliveryInformationSection;
    private Button btnShowContactUs;
    private LinearLayout hiddenSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the specific layout for InfoActivity within the content frame
        getLayoutInflater().inflate(R.layout.activity_info, findViewById(R.id.content_frame));

        // Initialize all the UI components
        btnFAQ = findViewById(R.id.btnFAQ);
        faqInformationSection = findViewById(R.id.faqInformationSection);
        btnDeliveryQuestions = findViewById(R.id.btnDeliveryQuestions);
        deliveryInformationSection = findViewById(R.id.deliveryInformationSection);
        btnShowContactUs = findViewById(R.id.btnShowContactUs);
        hiddenSection = findViewById(R.id.hiddenSection);

        // Set onClickListener to toggle the visibility of FAQ information section
        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(faqInformationSection);
            }
        });

        // Set onClickListener to toggle the visibility of delivery information section
        btnDeliveryQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(deliveryInformationSection);
            }
        });

        // Set onClickListener to navigate to ContactUsActivity
        btnShowContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });

        // Load and set HTML content for FAQ information
        TextView faqInfoTextView = findViewById(R.id.faqInformation);
        String faqInformation = getString(R.string.faq_content);
        faqInfoTextView.setText(Html.fromHtml(faqInformation));

        // Load and set HTML content for delivery information
        TextView deliveryInfoTextView = findViewById(R.id.deliveryInformation);
        String deliveryInformation = getString(R.string.delivery_information);
        deliveryInfoTextView.setText(Html.fromHtml(deliveryInformation));
    }

    // Helper method to toggle visibility of a LinearLayout
    private void toggleVisibility(LinearLayout layout) {
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }
}
