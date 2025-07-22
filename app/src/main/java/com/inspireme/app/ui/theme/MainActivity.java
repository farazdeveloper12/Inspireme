package com.inspireme.app.ui.theme;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inspireme.app.R;

public class MainActivity extends AppCompatActivity {

    private TextView quoteText;
    private TextView authorText;
    private Button refreshButton;
    private Button addWidgetButton;
    private Button favoriteButton;
    private Button shareButton;
    private Button categoriesButton;
    private Quote currentQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        quoteText = findViewById(R.id.quote_text);
        authorText = findViewById(R.id.author_text);
        refreshButton = findViewById(R.id.refresh_button);
        addWidgetButton = findViewById(R.id.add_widget_button);
        favoriteButton = findViewById(R.id.favorite_button);
        shareButton = findViewById(R.id.share_button);
        categoriesButton = findViewById(R.id.categories_button);

        // Display initial quote
        displayRandomQuote();

        // Set button click listeners
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomQuote();
            }
        });

        addWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWidgetPicker();
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote();
            }
        });

        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayRandomQuote() {
        currentQuote = QuoteManager.getRandomQuote();
        quoteText.setText(currentQuote.getText());
        authorText.setText("- " + currentQuote.getAuthor());
        updateFavoriteButton();
    }

    private void toggleFavorite() {
        if (currentQuote != null) {
            String quoteString = currentQuote.getText() + " - " + currentQuote.getAuthor();
            boolean isFavorite = FavoriteManager.toggleFavorite(this, quoteString);
            updateFavoriteButton();
        }
    }

    private void updateFavoriteButton() {
        if (currentQuote != null) {
            String quoteString = currentQuote.getText() + " - " + currentQuote.getAuthor();
            boolean isFavorite = FavoriteManager.isFavorite(this, quoteString);
            favoriteButton.setText(isFavorite ? "♥ Favorited" : "♡ Add to Favorites");
        }
    }

    private void shareQuote() {
        if (currentQuote != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareText = "\"" + currentQuote.getText() + "\" - " + currentQuote.getAuthor() +
                    "\n\nShared via InspireMe App";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Share Quote"));
        }
    }

    private void openWidgetPicker() {
        try {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            startActivity(intent);
        } catch (Exception e) {
            // If widget picker fails, just show a simple message
            // In a real app, you'd show a toast or dialog
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentQuote != null) {
            updateFavoriteButton();
        }
    }
}