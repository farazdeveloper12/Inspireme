package com.inspireme.app.ui.theme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inspireme.app.R;

public class CategoriesActivity extends AppCompatActivity {

    private ListView categoriesListView;
    private String[] categories = {
            "Motivational",
            "Success",
            "Life",
            "Happiness",
            "Leadership",
            "Dreams",
            "Wisdom",
            "Inspirational"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quote Categories");

        // Initialize views
        categoriesListView = findViewById(R.id.categories_list);

        // Setup list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, categories);
        categoriesListView.setAdapter(adapter);

        // Set click listener
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                Intent intent = new Intent(CategoriesActivity.this, CategoryQuotesActivity.class);
                intent.putExtra("category", selectedCategory);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}