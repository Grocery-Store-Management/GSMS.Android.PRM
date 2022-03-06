package com.prm.gsms.activities.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.prm.gsms.R;


public class CustomerPreferenceActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.customerpreference);
        SharedPreferences sharedPrefs = getSharedPreferences("com.prm.gsms_customer_preferences", MODE_PRIVATE);
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            initData(getPreferenceScreen().getPreference(i), sharedPrefs);
        }
    }

    private void initData(Preference p, SharedPreferences sharedPreferences) {
        if (p instanceof PreferenceCategory) {
            PreferenceCategory category = (PreferenceCategory) p;
            for (int i = 0; i < category.getPreferenceCount(); i++) {
                initData(category.getPreference(i), sharedPreferences);
            }
        } else {
            updatePreference(p, sharedPreferences);
        }
    }

    private void updatePreference(Preference p, SharedPreferences sharedPreferences) {
        if (p instanceof EditTextPreference) {
            EditTextPreference edt = (EditTextPreference) p;
            p.setSummary(edt.getText());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        updatePreference(findPreference(s), sharedPreferences);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
