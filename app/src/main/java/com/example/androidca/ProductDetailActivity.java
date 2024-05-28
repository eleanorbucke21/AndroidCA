package com.example.androidca;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productDescription, productPrice, productRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);

        String productString = getIntent().getStringExtra("product");
        try {
            JSONObject product = new JSONObject(productString);
            JSONObject fields = product.getJSONObject("fields");

            productName.setText(fields.getString("name"));
            productDescription.setText(fields.getString("description"));
            productPrice.setText("$" + fields.getDouble("price"));
            productRating.setText("Rating: " + fields.getDouble("rating"));

            Glide.with(this)
                    .load(fields.getString("image_url"))
                    .into(productImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
