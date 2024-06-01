package com.example.androidca.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidca.BaseActivity;
import com.example.androidca.R;
import com.example.androidca.data.OrderDAO;
import com.example.androidca.data.UserDAO;
import com.example.androidca.models.Order;
import com.example.androidca.models.User;

import java.util.List;

public class ProfileActivity extends BaseActivity {

    private EditText editTextName, editTextAddressLine1, editTextAddressLine2, editTextAddressLine3, editTextPostcode, editTextCountry;
    private Button buttonUpdate;
    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private TextView deliveryAddressTitle, orderHistoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, findViewById(R.id.content_frame));

        editTextName = findViewById(R.id.editTextName);
        editTextAddressLine1 = findViewById(R.id.editTextAddressLine1);
        editTextAddressLine2 = findViewById(R.id.editTextAddressLine2);
        editTextAddressLine3 = findViewById(R.id.editTextAddressLine3);
        editTextPostcode = findViewById(R.id.editTextPostcode);
        editTextCountry = findViewById(R.id.editTextCountry);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        recyclerView = findViewById(R.id.recyclerViewOrders);

        deliveryAddressTitle = findViewById(R.id.deliveryAddressTitle);
        orderHistoryTitle = findViewById(R.id.orderHistoryTitle);

        // Underline the text programmatically
        SpannableString deliveryAddressText = new SpannableString(getString(R.string.delivery_address));
        deliveryAddressText.setSpan(new UnderlineSpan(), 0, deliveryAddressText.length(), 0);
        deliveryAddressTitle.setText(deliveryAddressText);

        SpannableString orderHistoryText = new SpannableString(getString(R.string.order_history));
        orderHistoryText.setSpan(new UnderlineSpan(), 0, orderHistoryText.length(), 0);
        orderHistoryTitle.setText(orderHistoryText);

        userDAO = new UserDAO(this);
        orderDAO = new OrderDAO(this);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load user information
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            User user = userDAO.getUserInfo(username);
            if (user != null) {
                editTextName.setText(user.getName());
                editTextAddressLine1.setText(user.getAddressLine1());
                editTextAddressLine2.setText(user.getAddressLine2());
                editTextAddressLine3.setText(user.getAddressLine3());
                editTextPostcode.setText(user.getPostcode());
                editTextCountry.setText(user.getCountry());

                // Load and display order history
                List<Order> orders = orderDAO.getOrders(user.getId());
                orderAdapter = new OrderAdapter(orders);
                recyclerView.setAdapter(orderAdapter);
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        buttonUpdate.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String addressLine1 = editTextAddressLine1.getText().toString();
            String addressLine2 = editTextAddressLine2.getText().toString();
            String addressLine3 = editTextAddressLine3.getText().toString();
            String postcode = editTextPostcode.getText().toString();
            String country = editTextCountry.getText().toString();

            if (name.isEmpty() || addressLine1.isEmpty() || postcode.isEmpty() || country.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDAO.updateUserInfo(username, name, addressLine1, addressLine2, addressLine3, postcode, country)) {
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
