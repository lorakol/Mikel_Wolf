package com.training.ce35;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Schedule the download article worker using the unique periodic enqueue method
            NewsWorker.schedulePeriodicWork(context);
        }
    }
}

