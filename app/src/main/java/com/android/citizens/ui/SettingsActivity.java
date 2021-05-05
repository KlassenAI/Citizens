package com.android.citizens.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.citizens.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Настройки");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}