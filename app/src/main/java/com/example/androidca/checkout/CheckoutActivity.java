package com.example.androidca.checkout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidca.BaseActivity;
import com.example.androidca.R;
import com.example.androidca.utils.Constants;

public class CheckoutActivity extends BaseActivity {

    private EditText cardNumberEditText, cardExpiryEditText, cardCVVEditText, deliveryAddressEditText;
    private Button buyButton;
    private SharedPreferences sharedPreferences;
    private static final String TAG = "CheckoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_checkout, findViewById(R.id.content_frame));

        setupViews();
        preFillDeliveryAddress();
    }

    private void setupViews() {
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        cardExpiryEditText = findViewById(R.id.cardExpiryEditText);
        cardCVVEditText = findViewById(R.id.cardCVVEditText);
        deliveryAddressEditText = findViewById(R.id.deliveryAddressEditText);
        buyButton = findViewById(R.id.buyButton);

        buyButton.setOnClickListener(v -> processPurchase());
    }

    private void preFillDeliveryAddress() {
        sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        boolean isSignedIn = sharedPreferences.getBoolean(Constants.KEY_IS_SIGNED_IN, false);
        if (isSignedIn) {
            String userAddress = sharedPreferences.getString(Constants.KEY_USER_ADDRESS, "");
            deliveryAddressEditText.setText(userAddress);
        }
    }

    private void processPurchase() {
        String cardNumber = cardNumberEditText.getText().toString();
        String cardExpiry = cardExpiryEditText.getText().toString();
        String cardCVV = cardCVVEditText.getText().toString();
        String deliveryAddress = deliveryAddressEditText.getText().toString();

        // Fake purchase processing
        Log.d(TAG, "Processing purchase with card: " + cardNumber + ", expiry: " + cardExpiry + ", CVV: " + cardCVV);
        Log.d(TAG, "Delivery Address: " + deliveryAddress);

        Toast.makeText(this, "Purchase successful", Toast.LENGTH_SHORT).show();
    }
}
