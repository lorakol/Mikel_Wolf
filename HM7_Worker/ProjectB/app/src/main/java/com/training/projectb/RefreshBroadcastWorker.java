package com.training.projectb;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class RefreshBroadcastWorker extends Worker {

    //private static final String ACTION_REFRESH_UI = "refresh_ui";
    private static final String ACTION_REFRESH_UI = "com.training.projectb.ACTION_REFRESH_UI";
    public RefreshBroadcastWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Intent updateIntent = new Intent(ACTION_REFRESH_UI);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(updateIntent);
        return Result.success();
    }
}
