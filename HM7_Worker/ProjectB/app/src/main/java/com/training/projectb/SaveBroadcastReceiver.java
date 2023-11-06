package com.training.projectb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class SaveBroadcastReceiver extends BroadcastReceiver {
    private Context comContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        comContext = context;
        String article = intent.getStringExtra("article");

        // Schedule the save article worker using the OneTimeWorkRequest
        scheduleSaveArticleWorker(article);
    }

    private void scheduleSaveArticleWorker(String article) {
        // Create an input data for the save article worker
        Data inputData = new Data.Builder()
                .putString("article", article)
                .build();

        // Create a one-time worker to save the article
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(SaveArticleWorker.class)
                .setInputData(inputData)
                .build();

        WorkManager workManager = WorkManager.getInstance(comContext);
        workManager.enqueue(workRequest);
    }
}
