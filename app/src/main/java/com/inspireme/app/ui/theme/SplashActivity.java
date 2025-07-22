package com.inspireme.app.ui.theme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.inspireme.app.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find views (safely - with null checks)
        TextView appName = findViewById(R.id.splash_app_name);
        TextView tagline = findViewById(R.id.splash_tagline);

        // Set text (in case layout doesn't load properly)
        if (appName != null) {
            appName.setText("InspireMe");
        }
        if (tagline != null) {
            tagline.setText("Daily Motivation for Success");
        }

        // Navigate to MainActivity after delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close splash activity
            }
        }, SPLASH_DURATION);
    }
}