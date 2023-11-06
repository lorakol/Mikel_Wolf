package com.training.unittestingstarter.fragment;

import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;

import com.training.unittestingstarter.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String TAG = "SettingsFragment.TAG";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.format_prefs_screen);//format_prefs_screen
    }
}
