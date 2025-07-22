package com.inspireme.app.ui.theme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inspireme.app.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch dailyNotificationSwitch;
    private Switch darkModeSwitch;
    private Switch autoRefreshSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("InspireMeSettings", MODE_PRIVATE);

        // Initialize views
        dailyNotificationSwitch = findViewById(R.id.daily_notification_switch);
        darkModeSwitch = findViewById(R.id.dark_mode_switch);
        autoRefreshSwitch = findViewById(R.id.auto_refresh_switch);

        // Load current settings
        loadSettings();

        // Set up listeners
        dailyNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSetting("daily_notifications", isChecked);
            }
        });

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSetting("dark_mode", isChecked);
            }
        });

        autoRefreshSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSetting("auto_refresh", isChecked);
            }
        });
    }

    private void loadSettings() {
        dailyNotificationSwitch.setChecked(sharedPreferences.getBoolean("daily_notifications", true));
        darkModeSwitch.setChecked(sharedPreferences.getBoolean("dark_mode", false));
        autoRefreshSwitch.setChecked(sharedPreferences.getBoolean("auto_refresh", true));
    }

    private void saveSetting(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
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