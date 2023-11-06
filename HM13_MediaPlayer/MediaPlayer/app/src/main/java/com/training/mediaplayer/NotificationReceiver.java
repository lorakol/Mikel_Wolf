package com.training.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String ACTION_PLAY = "com.training.mediaplayer.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.training.mediaplayer.ACTION_PAUSE";
    public static final String ACTION_NEXT = "com.training.mediaplayer.ACTION_NEXT";
    public static final String ACTION_PREV = "com.training.mediaplayer.ACTION_PREV";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action == null) return;

        Intent serviceIntent = new Intent(context, MediaPlayerService.class);

        switch (action) {
            case ACTION_PLAY:
                serviceIntent.setAction(ACTION_PLAY);
                context.startService(serviceIntent);
                break;
            case ACTION_PAUSE:
                serviceIntent.setAction(ACTION_PAUSE);
                context.startService(serviceIntent);
                break;
            case ACTION_NEXT:
                serviceIntent.setAction(ACTION_NEXT);
                context.startService(serviceIntent);
                break;
            case ACTION_PREV:
                serviceIntent.setAction(ACTION_PREV);
                context.startService(serviceIntent);
                break;
        }
    }
}
