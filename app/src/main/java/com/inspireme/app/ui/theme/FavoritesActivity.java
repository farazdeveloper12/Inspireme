package com.inspireme.app.ui.theme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inspireme.app.R;
import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private ListView favoritesListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> favoriteQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Favorite Quotes");
        }

        // Initialize views
        favoritesListView = findViewById(R.id.favorites_list);

        // Load favorite quotes
        loadFavorites();

        // Set click listener for sharing favorites
        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuote = favoriteQuotes.get(position);
                shareQuote(selectedQuote);
            }
        });

        // Set long click listener for removing favorites
        favoritesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuote = favoriteQuotes.get(position);
                FavoriteManager.removeFavorite(FavoritesActivity.this, selectedQuote);
                loadFavorites(); // Refresh the list
                Toast.makeText(FavoritesActivity.this, "Quote removed from favorites", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void loadFavorites() {
        favoriteQuotes = FavoriteManager.getFavoriteQuotes(this);

        if (favoriteQuotes.isEmpty()) {
            // Show message when no favorites
            favoriteQuotes.add("No favorite quotes yet!");
            favoriteQuotes.add("Tap the ♡ heart button to add quotes to your favorites");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteQuotes);
        favoritesListView.setAdapter(adapter);
    }

    private void shareQuote(String quote) {
        if (quote.contains("No favorite quotes yet") || quote.contains("Tap the")) {
            Toast.makeText(this, "Add some favorite quotes first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareText = "\"" + quote + "\"\n\nShared via InspireMe App";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Share Favorite Quote"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }
}