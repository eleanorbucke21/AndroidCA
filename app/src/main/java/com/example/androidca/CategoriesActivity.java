package com.example.androidca;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoriesActivity extends BaseActivity {

    private ListView categoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the specific layout for CategoriesActivity within the content frame
        getLayoutInflater().inflate(R.layout.activity_categories, findViewById(R.id.content_frame));

        categoriesListView = findViewById(R.id.categoriesListView);

        // Load and parse categories from JSON
        ArrayList<String> categories = loadCategoriesFromJSON();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoriesListView.setAdapter(adapter);
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
