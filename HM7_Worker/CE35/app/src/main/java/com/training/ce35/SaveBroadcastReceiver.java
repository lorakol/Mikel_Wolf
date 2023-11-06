package com.training.ce35;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SaveBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_SAVE_ARTICLE = "save_article";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals(ACTION_SAVE_ARTICLE)) {
            String article = intent.getStringExtra("article");

            // Schedule the save article worker using the OneTimeWorkRequest
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SaveArticleWorker.class)
                    .setInputData(createInputData(article))
                    .build();
            WorkManager.getInstance(context).enqueue(workRequest);
        }
    }

    private Data createInputData(String article) {
        return new Data.Builder()
                .putString("article", article)
                .build();
    }
}

