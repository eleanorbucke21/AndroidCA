package com.example.androidca.products;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidca.BaseActivity;
import com.example.androidca.R;
import com.example.androidca.bag.BagManager;

import org.json.JSONObject;

public class ProductDetailActivity extends BaseActivity {

    private ImageView productImage;
    private TextView productName, productDescription, productPrice, productRating;
    private EditText quantityEditText;
    private Button decrementButton, incrementButton, addToBagButton, keepShoppingButton;

    private JSONObject product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // BaseActivity sets the content view to R.layout.activity_base

        // Find the FrameLayout and inflate your specific layout
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_product_detail, contentFrame);

        // Initialize views and set up buttons
        setupViews();
        updateButtonVisibility(); // Ensure this is called to update button visibility
    }

    private void setupViews() {
        // Initialize your views here
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);
        quantityEditText = findViewById(R.id.quantityEditText);
        decrementButton = findViewById(R.id.decrementButton);
        incrementButton = findViewById(R.id.incrementButton);
        addToBagButton = findViewById(R.id.addToBagButton);
        keepShoppingButton = findViewById(R.id.keepShoppingButton);

        initializeProductDetails();
    }

    private void initializeProductDetails() {
        String productString = getIntent().getStringExtra("product");
        Log.d("ProductDetailActivity", "Product String: " + productString);
        if (productString == null) {
            Log.e("ProductDetailActivity", "Product string is null");
            return;
        }

        try {
            product = new JSONObject(productString);
            JSONObject fields = product.getJSONObject("fields");

            productName.setText(fields.getString("name"));
            productDescription.setText(fields.getString("description"));
            productPrice.setText("$" + fields.getDouble("price"));
            productRating.setText("Rating: " + fields.getDouble("rating"));

            String imageUrl = fields.getString("image_url");
            Log.d("ProductDetailActivity", "Image URL: " + imageUrl);

            Glide.with(this)
                    .load(imageUrl)
                    .into(productImage);

            setupListeners();
        } catch (Exception e) {
            Log.e("ProductDetailActivity", "Error parsing product JSON", e);
        }
    }

    private void setupListeners() {
        decrementButton.setOnClickListener(v -> {
            String quantityText = quantityEditText.getText().toString();
            if (!quantityText.isEmpty()) {
                int quantity = Integer.parseInt(quantityText);
                if (quantity > 1) {
                    quantity--;
                    quantityEditText.setText(String.valueOf(quantity));
                }
            }
        });

        incrementButton.setOnClickListener(v -> {
            String quantityText = quantityEditText.getText().toString();
            if (!quantityText.isEmpty()) {
                int quantity = Integer.parseInt(quantityText);
                if (quantity < 99) {
                    quantity++;
                    quantityEditText.setText(String.valueOf(quantity));
                }
            }
        });

        addToBagButton.setOnClickListener(v -> addToBag());
        keepShoppingButton.setOnClickListener(v -> finish());
    }

    private void addToBag() {
        try {
            JSONObject productWithQuantity = new JSONObject(product.toString());
            productWithQuantity.put("quantity", Integer.parseInt(quantityEditText.getText().toString()));
            BagManager.getInstance().addToBag(productWithQuantity);
            Toast.makeText(this, "Added to Bag", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("ProductDetailActivity", "Error adding product to bag", e);
        }
    }
}
