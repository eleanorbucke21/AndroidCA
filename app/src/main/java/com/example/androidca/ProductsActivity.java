package com.example.androidca;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products); // Ensure this matches your layout file name

        productsRecyclerView = findViewById(R.id.productsRecyclerView); // Ensure this matches the id in your layout file
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<JSONObject> products = loadProductsFromJSON();
        productAdapter = new ProductAdapter(products);
        productsRecyclerView.setAdapter(productAdapter);
    }

    private ArrayList<JSONObject> loadProductsFromJSON() {
        ArrayList<JSONObject> products = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.products);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                products.add(jsonArray.getJSONObject(i));
            }
        } catch (Exception e) {
            Log.e("ProductsActivity", "Error loading JSON", e);
        }
        return products;
    }
}
