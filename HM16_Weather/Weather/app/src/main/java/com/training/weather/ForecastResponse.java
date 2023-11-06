package com.training.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("list")
    public List<WeatherResponse> weatherList;
}
