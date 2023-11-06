package com.training.ce35;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UiRefreshBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_REFRESH_UI = "refresh_ui";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Reload the application's UI to show the newly saved article
        // Replace this with your own logic to refresh the UI

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals(ACTION_REFRESH_UI)) {
                // Reload the application's UI to show the newly saved article
                // Replace this with your own logic to refresh the UI
            }
        }
    }
}

