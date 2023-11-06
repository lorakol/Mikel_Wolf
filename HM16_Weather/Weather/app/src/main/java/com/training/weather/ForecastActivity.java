package com.training.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends Activity {

    WeatherService service;
    LinearLayout forecastLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        forecastLayout = findViewById(R.id.forecast_layout);
        service = new WeatherService();
        // Start the AsyncTask to fetch data
        forecastLayout.setBackgroundResource(MainActivity.main_back);
        fetchForecastFromAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        forecastLayout.setBackgroundResource(MainActivity.main_back);
    }

    private void fetchForecastFromAPI() {
        // Placeholder: implement your API call here
        Call<ForecastResponse> call = service.fetchForecast(MainActivity.currentCity); // Replace "London" with your desired city

        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful()) {

                    updateUI(response.body());
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                // Handle error
                int n = 0;
            }
        });
    }

    private void updateUI(ForecastResponse forecastData) {
        // Example UI update using forecast data

        ImageView day1Icon = findViewById(R.id.day1_icon);
        TextView day1High = findViewById(R.id.day1_high);
        TextView day1Low = findViewById(R.id.day1_low);
        TextView day1date = findViewById(R.id.day1_date);

        WeatherResponse day1 = forecastData.weatherList.get(7);

        day1Icon.setImageResource(getWeatherIconRes(day1.weather.get(0).icon));
        day1High.setText(String.valueOf(day1.main.temp_max));
        day1Low.setText(String.valueOf(day1.main.temp_min));
        day1date.setText(new SimpleDateFormat("MM dd, yyyy hh:mma").format(new Date(day1.dt*1000)));

        // Repeat for day2 and day3...

        ImageView day2Icon = findViewById(R.id.day2_icon);
        TextView day2High = findViewById(R.id.day2_high);
        TextView day2Low = findViewById(R.id.day2_low);
        TextView day2date = findViewById(R.id.day2_date);

        WeatherResponse day2 = forecastData.weatherList.get(15);

        day2Icon.setImageResource(getWeatherIconRes(day2.weather.get(0).icon));
        day2High.setText(String.valueOf(day2.main.temp_max));
        day2Low.setText(String.valueOf(day2.main.temp_min));
        day2date.setText(new SimpleDateFormat("MM dd, yyyy hh:mma").format(new Date(day2.dt*1000)));

        ImageView day3Icon = findViewById(R.id.day3_icon);
        TextView day3High = findViewById(R.id.day3_high);
        TextView day3Low = findViewById(R.id.day3_low);
        TextView day3date = findViewById(R.id.day3_date);

        WeatherResponse day3 = forecastData.weatherList.get(8);

        day3Icon.setImageResource(getWeatherIconRes(day3.weather.get(0).icon));
        day3High.setText(String.valueOf(day3.main.temp_max));
        day3Low.setText(String.valueOf(day3.main.temp_min));
        day3date.setText(new SimpleDateFormat("MM dd, yyyy hh:mma").format(new Date(day3.dt*1000)));
    }

    private int getWeatherIconRes(String condition) {
        // Placeholder: Map your condition to a drawable resource ID
        String strIcon = condition.substring(1, condition.length() - 1);
        int nStatus = Integer.parseInt(strIcon);
        if(nStatus == 1)
            return R.drawable.nice;
        else if(nStatus < 5)
            return R.drawable.cloud;
        else
            return R.drawable.rain;

    }


}
