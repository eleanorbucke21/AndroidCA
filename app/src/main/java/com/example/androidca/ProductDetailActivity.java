package com.example.androidca;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
        getLayoutInflater().inflate(R.layout.activity_product_detail, findViewById(R.id.content_frame));

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

        String productString = getIntent().getStringExtra("product");
        try {
            product = new JSONObject(productString);
            JSONObject fields = product.getJSONObject("fields");

            productName.setText(fields.getString("name"));
            productDescription.setText(fields.getString("description"));
            productPrice.setText("$" + fields.getDouble("price"));
            productRating.setText("Rating: " + fields.getDouble("rating"));

            Glide.with(this)
                    .load(fields.getString("image_url"))
                    .into(productImage);

            decrementButton.setOnClickListener(v -> {
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    quantityEditText.setText(String.valueOf(quantity));
                }
            });

            incrementButton.setOnClickListener(v -> {
                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                if (quantity < 99) {
                    quantity++;
                    quantityEditText.setText(String.valueOf(quantity));
                }
            });

            addToBagButton.setOnClickListener(v -> {
                addToBag();
            });

            keepShoppingButton.setOnClickListener(v -> {
                finish();
            });

        } catch (Exception e) {
            Log.e("ProductDetailActivity", "Error parsing product JSON", e);
        }
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
