package com.inspireme.app.ui.theme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inspireme.app.R;

public class CategoryQuotesActivity extends AppCompatActivity {

    private TextView quoteText;
    private TextView authorText;
    private Button nextQuoteButton;
    private Button favoriteButton;
    private Button shareButton;
    private String category;
    private Quote currentQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_quotes);

        // Get category from intent
        category = getIntent().getStringExtra("category");

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(category + " Quotes");

        // Initialize views
        quoteText = findViewById(R.id.quote_text);
        authorText = findViewById(R.id.author_text);
        nextQuoteButton = findViewById(R.id.next_quote_button);
        favoriteButton = findViewById(R.id.favorite_button);
        shareButton = findViewById(R.id.share_button);

        // Display first quote
        displayRandomQuote();

        // Set button listeners
        nextQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomQuote();
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
    }

    private void displayRandomQuote() {
        currentQuote = QuoteManager.getRandomQuoteByCategory(category);
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
            favoriteButton.setText(isFavorite ? "♥ Remove from Favorites" : "♡ Add to Favorites");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}