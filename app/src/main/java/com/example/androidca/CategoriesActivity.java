package com.example.androidca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class CategoriesActivity extends BaseActivity {

    private ListView categoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_categories, findViewById(R.id.content_frame));

        categoriesListView = findViewById(R.id.categoriesListView);

        ArrayList<String> categories = loadCategoriesFromJSON();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoriesListView.setAdapter(adapter);

        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = String.valueOf(position + 1); // Assuming category IDs start from 1 and are sequential
                Intent intent = new Intent(CategoriesActivity.this, ProductsActivity.class);
                intent.putExtra("category", selectedCategory);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetBottomNavigationSelection();
    }

    private ArrayList<String> loadCategoriesFromJSON() {
        ArrayList<String> categories = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.categories);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject category = jsonArray.getJSONObject(i);
                JSONObject fields = category.getJSONObject("fields");
                categories.add(fields.getString("friendly_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}
