package com.example.androidca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

public class BagActivity extends BaseActivity {

    private RecyclerView bagRecyclerView;
    private BagAdapter bagAdapter;
    private TextView bagTotal, delivery, grandTotal;
    private Button checkoutButton, keepShoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_bag, findViewById(R.id.content_frame));

        bagRecyclerView = findViewById(R.id.bagRecyclerView);
        bagTotal = findViewById(R.id.bagTotal);
        delivery = findViewById(R.id.delivery);
        grandTotal = findViewById(R.id.grandTotal);
        checkoutButton = findViewById(R.id.checkoutButton);
        keepShoppingButton = findViewById(R.id.keepShoppingButton);

        bagRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bagAdapter = new BagAdapter(BagManager.getInstance().getBagItems());
        bagRecyclerView.setAdapter(bagAdapter);

        updateTotals();

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
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetBottomNavigationSelection();
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

        double deliveryCost = total > 50 ? 0.0 : 5.0; // Example logic for delivery cost
        double grandTotalValue = total + deliveryCost;

        bagTotal.setText(String.format(getString(R.string.bag_total), total));
        delivery.setText(String.format(getString(R.string.delivery), deliveryCost));
        grandTotal.setText(String.format(getString(R.string.grand_total), grandTotalValue));
    }
}
