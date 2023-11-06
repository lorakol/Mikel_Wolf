package com.training.weather;

// WeatherAPI.java
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("data/2.5/weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    @GET("data/2.5/forecast")
    Call<ForecastResponse> getCurrentForecast(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}

