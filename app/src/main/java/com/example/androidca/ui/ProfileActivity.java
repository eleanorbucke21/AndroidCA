package com.example.androidca.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidca.R;
import com.example.androidca.data.OrderDAO;
import com.example.androidca.data.UserDAO;
import com.example.androidca.models.Order;
import com.example.androidca.models.User;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private EditText editTextName, editTextAddress;
    private Button buttonUpdate;
    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private TextView deliveryAddressTitle, orderHistoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
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
                editTextName.setText(user.getUsername());
                editTextAddress.setText(user.getAddress());

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
            String address = editTextAddress.getText().toString();

            if (userDAO.updateUserInfo(username, name, address)) {
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
