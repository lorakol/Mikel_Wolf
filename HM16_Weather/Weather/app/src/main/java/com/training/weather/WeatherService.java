package com.training.weather;

// WeatherService.java
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {
    private static final String BASE_WEATHER_URL = "https://api.openweathermap.org/";
    private static final String BASE_FORECAST_URL = "https://api.openweathermap.org/";
    private static final String API_KEY = "6222d90a9b5a16a89439e316b66758f1";
    private WeatherAPI weatherAPI;
    private WeatherAPI forecastAPI;

    public WeatherService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherAPI = retrofit.create(WeatherAPI.class);

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(BASE_FORECAST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        forecastAPI = retrofit1.create(WeatherAPI.class);
    }

    public Call<WeatherResponse> fetchWeather(String city) {
        return weatherAPI.getCurrentWeather(city, API_KEY, "metric"); // 'metric' for Celsius
    }

    public Call<ForecastResponse> fetchForecast(String city) {
        return forecastAPI.getCurrentForecast(city, API_KEY, "metric"); // 'metric' for Celsius
    }
}

