package com.training.weather;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class WidgetPreferenceFragment extends PreferenceFragment {

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    private void setPreferencesFromResource(int preferences, String rootKey) {


    }
}
