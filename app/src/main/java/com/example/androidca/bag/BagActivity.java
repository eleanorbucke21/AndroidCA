package com.example.androidca.bag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidca.BaseActivity;
import com.example.androidca.R;
import com.example.androidca.checkout.CheckoutActivity;
import com.example.androidca.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class BagActivity extends BaseActivity {

    private RecyclerView bagRecyclerView;
    private BagAdapter bagAdapter;
    private TextView bagTotal, delivery, grandTotal;
    private Button checkoutButton, keepShoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_bag, findViewById(R.id.content_frame));

        // Setup the recycler view for showing bag items
        bagRecyclerView = findViewById(R.id.bagRecyclerView);
        bagRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bagAdapter = new BagAdapter(BagManager.getInstance().getBagItems());
        bagRecyclerView.setAdapter(bagAdapter);

        // Reference to the UI components
        bagTotal = findViewById(R.id.bagTotal);
        delivery = findViewById(R.id.delivery);
        grandTotal = findViewById(R.id.grandTotal);
        checkoutButton = findViewById(R.id.checkoutButton);
        keepShoppingButton = findViewById(R.id.keepShoppingButton);

        // Initialize totals
        updateTotals();

        // Setup button listeners
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CheckoutActivity
                Intent intent = new Intent(BagActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        keepShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close this activity
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure navigation is correctly set when resuming the activity
        resetBottomNavigationSelection();
    }

    /**
     * Check if the user is currently logged in using shared preferences.
     *
     * @return true if the user is logged in, false otherwise.
     */
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.KEY_IS_SIGNED_IN, false);
    }

    private void updateTotals() {
        ArrayList<JSONObject> bagItems = BagManager.getInstance().getBagItems();
        double total = 0.0;
        for (JSONObject item : bagItems) {
            try {
                double price = item.getJSONObject("fields").getDouble("price");
                int quantity = item.getInt("quantity");
                total += price * quantity;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        double discount = 0;
        if (isUserLoggedIn()) {
            discount = total * 0.1; // Calculate 10% discount
            total -= discount; // Apply the discount
        }

        double deliveryCost = total > 50 ? 0.0 : 5.0;
        double grandTotal = total + deliveryCost;

        // Set the text views using string resources
        TextView bagTotalView = findViewById(R.id.bagTotal);
        TextView deliveryView = findViewById(R.id.delivery);
        TextView discountView = findViewById(R.id.discountNotification);
        TextView grandTotalView = findViewById(R.id.grandTotal);

        bagTotalView.setText(getString(R.string.bag_total_label, String.format(Locale.UK, "%.2f", total + discount)));
        deliveryView.setText(getString(R.string.delivery_label, String.format(Locale.UK, "%.2f", deliveryCost)));
        grandTotalView.setText(getString(R.string.grand_total_label, String.format(Locale.UK, "%.2f", grandTotal)));

        if (discount > 0) {
            discountView.setVisibility(View.VISIBLE);
            discountView.setText(getString(R.string.discount_label, String.format(Locale.UK, "%.2f", discount)));
        } else {
            discountView.setVisibility(View.GONE);
        }
    }
}