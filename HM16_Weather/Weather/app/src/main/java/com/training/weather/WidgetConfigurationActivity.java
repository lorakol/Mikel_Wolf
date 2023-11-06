package com.training.weather;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;

public class WidgetConfigurationActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private AppWidgetManager appWidgetManager;
    int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appWidgetManager = AppWidgetManager.getInstance(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        addPreferencesFromResource(R.xml.preferences);
        // Register a listener for preference changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);


    }

    private void saveWidgetConfig() {
        // Save your widget configuration here (e.g., update Shared Preferences)

        // Once configuration is saved, return the result to the system
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }





    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the preference change listener
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("theme_preference")) {
            // Handle theme preference change
            updateWidgetAppearance();
        } else if (key.equals("city_preference")) {
            // Handle city preference change
            updateWidgetAppearance();
        }
    }

    private void updateWidgetAppearance() {
        // Get the selected theme and city from preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedTheme = preferences.getString("theme_preference", "");
        String selectedCity = preferences.getString("city_preference", "");

        // Update the widget appearance based on the selected theme and city
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.weather_widget_layout);
        if (selectedTheme.equals("light")) {
            // Set light theme styling for the widget
            remoteViews.setInt(R.id.widget_layout, "setBackgroundResource", R.drawable.light_back);
            MainActivity.main_back = R.drawable.light_back;
        } else if (selectedTheme.equals("dark")) {
            // Set dark theme styling for the widget
            remoteViews.setInt(R.id.widget_layout, "setBackgroundResource", R.drawable.dark_back);
            MainActivity.main_back = R.drawable.dark_back;
        }
        // Update other widget elements based on the selected city
        saveWidgetConfig();

        // Define how often you want to fetch the weather data. For example, 1 hour:
        MainActivity.currentCity = selectedCity;
        long repeatInterval = 1;
        TimeUnit timeUnit = TimeUnit.HOURS;

        PeriodicWorkRequest fetchWeatherDataWork = new PeriodicWorkRequest.Builder(WeatherDataWorker.class, repeatInterval, timeUnit)
                .build();

        Context context = null;
        WorkManager.getInstance(context).enqueue(fetchWeatherDataWork);


        // Update the widget layout
        appWidgetManager.updateAppWidget(mAppWidgetId, remoteViews);
    }
}
