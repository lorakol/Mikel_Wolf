package com.training.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataWorker extends Worker {
    public static String selectedCity = "London";
    public WeatherDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Fetch the weather data here and save it to a file
        // Return Result.success() if fetching was successful
        // or Result.failure() if it wasn't.
        fetchDataFromAPI();
        return Result.success();

    }

    private void saveDataToFile(String data) {
        File file = new File(getApplicationContext().getFilesDir(), "weatherData.txt");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchDataFromAPI(){
        WeatherService service = new WeatherService();
        Call<WeatherResponse> call = service.fetchWeather(selectedCity); // Replace "London" with your desired city

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherData = response.body();
                    // Update your UI with weatherData
                    saveDataToFile(weatherData.toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Handle error
            }
        });
    }
}
