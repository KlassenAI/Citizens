package com.android.citizens.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.citizens.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);

        mSharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            preference.setOnPreferenceChangeListener(this);
            String string = mSharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, string);
        }
    }

    private void setPreferenceSummary(Preference preference, String string) {
        if (preference instanceof EditTextPreference) {
            preference.setSummary(string);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (preference instanceof EditTextPreference) {
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPreferenceSummary(preference, value);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String string = (String) newValue;

        try {
            int value = Integer.parseInt(string);
            Toast toast = Toast.makeText(getContext(), "Введено значение вне диапазона", Toast.LENGTH_SHORT);
            switch (preference.getKey()) {
                case "period": {
                    if (value >= 2 && value <= 30) return true;
                    else {
                        toast.show();
                        return false;
                    }
                }
                case "min_age": {
                    int maxAge = Integer.parseInt(mSharedPreferences.getString("max_age", ""));
                    if (value >= 18 && value <= maxAge) return true;
                    else {
                        toast.show();
                        return false;
                    }
                }
                case "max_age": {
                    int minAge = Integer.parseInt(mSharedPreferences.getString("min_age", ""));
                    if (value >= minAge && value <= 60) return true;
                    else {
                        toast.show();
                        return false;
                    }
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Ошибка при вводе значения", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}