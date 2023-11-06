package com.training.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// WeatherResponse.java
public class WeatherResponse {
    @SerializedName("main")
    public Main main;
    @SerializedName("weather")
    public List<Weather> weather;
    public Long dt;

    public class Main {
        @SerializedName("temp")
        public float temp;
        @SerializedName("temp_min")
        public float temp_min;
        @SerializedName("temp_max")
        public float temp_max;
    }

    public static class Weather {
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +"main=" + main +", weather=" + weather +'}';
    }
}

