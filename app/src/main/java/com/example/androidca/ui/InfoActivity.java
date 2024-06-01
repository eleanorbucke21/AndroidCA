package com.example.androidca.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
        getLayoutInflater().inflate(R.layout.activity_info, findViewById(R.id.content_frame));

        btnFAQ = findViewById(R.id.btnFAQ);
        faqInformationSection = findViewById(R.id.faqInformationSection);
        btnDeliveryQuestions = findViewById(R.id.btnDeliveryQuestions);
        deliveryInformationSection = findViewById(R.id.deliveryInformationSection);
        btnShowContactUs = findViewById(R.id.btnShowContactUs);
        hiddenSection = findViewById(R.id.hiddenSection);

        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faqInformationSection.getVisibility() == View.GONE) {
                    faqInformationSection.setVisibility(View.VISIBLE);
                } else {
                    faqInformationSection.setVisibility(View.GONE);
                }
            }
        });

        btnDeliveryQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deliveryInformationSection.getVisibility() == View.GONE) {
                    deliveryInformationSection.setVisibility(View.VISIBLE);
                } else {
                    deliveryInformationSection.setVisibility(View.GONE);
                }
            }
        });

        btnShowContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenSection.getVisibility() == View.GONE) {
                    hiddenSection.setVisibility(View.VISIBLE);
                } else {
                    hiddenSection.setVisibility(View.GONE);
                }
            }
        });

        TextView faqInfoTextView = findViewById(R.id.faqInformation);
        String faqInformation = getString(R.string.faq_content);
        faqInfoTextView.setText(Html.fromHtml(faqInformation));

        TextView deliveryInfoTextView = findViewById(R.id.deliveryInformation);
        String deliveryInformation = getString(R.string.delivery_information);
        deliveryInfoTextView.setText(Html.fromHtml(deliveryInformation));
    }
}
