package com.example.androidca;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class ProductsActivity extends BaseActivity {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<JSONObject> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_products, findViewById(R.id.content_frame));

        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        allProducts = loadProductsFromJSON();
        displayProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetBottomNavigationSelection();
    }

    private void displayProducts() {
        String selectedCategory = getIntent().getStringExtra("category");
        ArrayList<JSONObject> productsToDisplay;

        if (selectedCategory != null) {
            // Debugging: Log the selected category
            Log.d("ProductsActivity", "Selected category: " + selectedCategory);
            productsToDisplay = filterProductsByCategory(allProducts, selectedCategory);
        } else {
            // No category selected, display all products
            productsToDisplay = allProducts;
        }

        // Debugging: Log the number of products to display
        Log.d("ProductsActivity", "Products to display count: " + productsToDisplay.size());

        productAdapter = new ProductAdapter(productsToDisplay);
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

    private ArrayList<JSONObject> filterProductsByCategory(ArrayList<JSONObject> products, String category) {
        ArrayList<JSONObject> filteredProducts = new ArrayList<>();
        try {
            for (JSONObject product : products) {
                JSONObject fields = product.getJSONObject("fields");
                String productCategory = fields.getString("category");

                // Debugging: Log the category comparison
                Log.d("ProductsActivity", "Product category: " + productCategory + ", Selected category: " + category);

                if (productCategory.equals(category)) {
                    filteredProducts.add(product);
                }
            }
        } catch (Exception e) {
            Log.e("ProductsActivity", "Error filtering products", e);
        }
        return filteredProducts;
    }
}
