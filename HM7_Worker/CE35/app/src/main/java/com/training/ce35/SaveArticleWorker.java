package com.training.ce35;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SaveArticleWorker extends Worker {

    private static final String ACTION_REFRESH_UI = "refresh_ui";

    public SaveArticleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String article = getInputData().getString("article");
        saveArticleToDatabase(article);

        // Send a UI refresh broadcast
        sendUiRefreshBroadcast();

        // Return the result of the work
        return Result.success();
    }

    private void saveArticleToDatabase(String article) {
        // Replace this with your own logic to save the article to the chosen storage method
        // For example, if using SQLite, you can use a database helper class to insert the article
        // into the database
    }

    private void sendUiRefreshBroadcast() {
        // Create a data object for the UI refresh broadcast
        Data outputData = new Data.Builder()
                .putString("action", ACTION_REFRESH_UI)
                .build();

        // Create a one-time work request to send the UI refresh broadcast
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(RefreshBroadcastWorker.class)
                .setInputData(outputData)
                .build();

        // Enqueue the work request
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
    }
}

