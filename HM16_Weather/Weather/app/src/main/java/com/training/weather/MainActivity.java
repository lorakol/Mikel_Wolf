package com.training.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static String currentCity = "London";
    public static int main_back = R.drawable.light_back;
    WeatherService service;
    WeatherResponse currentData;

    TextView txtCity, txtCondition, txtTemperature, txtTimestamp;
    ImageView imgWeather;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_widget_layout);
        mainLayout = findViewById(R.id.widget_layout);
        txtCity = findViewById(R.id.location_city);
        txtCondition = findViewById(R.id.weather_condition);
        txtTemperature = findViewById(R.id.temperature);
        txtTimestamp = findViewById(R.id.timestamp);
        imgWeather = findViewById(R.id.weather_icon);
        service = new WeatherService();

        Button configureButton = findViewById(R.id.configure_button);
        configureButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WidgetConfigurationActivity.class);
            startActivity(intent);
        });
        Button forecastButton = findViewById(R.id.forecast_button);
        forecastButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
            startActivity(intent);
        });
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        Call<WeatherResponse> call = service.fetchWeather(currentCity); // Replace "London" with your desired city

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    currentData = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Handle error
                int n = 0;
            }
        });
        mainLayout.setBackgroundResource(main_back);
    }

    private void updateUI(){
        txtCity.setText(currentCity);
        txtCondition.setText(currentData.weather.get(0).description);
        txtTemperature.setText(String.valueOf(currentData.main.temp));
        Date df = new java.util.Date(currentData.dt*1000);
        String strDate = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);
        txtTimestamp.setText(strDate);
        String strIcon = currentData.weather.get(0).icon.substring(1, currentData.weather.get(0).icon.length() - 1);
        int nStatus = Integer.parseInt(strIcon);
        if(nStatus == 1)
            imgWeather.setImageResource(R.drawable.nice);
        else if(nStatus < 5)
            imgWeather.setImageResource(R.drawable.cloud);
        else
            imgWeather.setImageResource(R.drawable.rain);

    }

}