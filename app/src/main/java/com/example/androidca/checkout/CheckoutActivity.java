package com.example.androidca.checkout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidca.BaseActivity;
import com.example.androidca.R;
import com.example.androidca.data.OrderDAO;
import com.example.androidca.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends BaseActivity {

    private EditText cardNumberEditText, cardExpiryEditText, cardCVVEditText, deliveryAddressEditText;
    private Button buyButton;
    private SharedPreferences sharedPreferences;
    private OrderDAO orderDAO;
    private static final String TAG = "CheckoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_checkout, findViewById(R.id.content_frame));

        setupViews();
        preFillDeliveryAddress();
        orderDAO = new OrderDAO(this);
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

        saveOrderToDatabase();

        Toast.makeText(this, "Purchase successful", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after purchase
    }

    private void saveOrderToDatabase() {
        sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt(Constants.KEY_USER_ID, -1);
        if (userId == -1) {
            Log.e(TAG, "User ID not found in SharedPreferences");
            return;
        }

        String orderDetails = "Order details go here"; // Replace with actual order details
        String orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        double totalAmount = 100.00; // Replace with the actual total amount
        String deliveryAddress = deliveryAddressEditText.getText().toString();

        orderDAO.addOrder(userId, orderDate, orderDetails, totalAmount, deliveryAddress);
    }
}
