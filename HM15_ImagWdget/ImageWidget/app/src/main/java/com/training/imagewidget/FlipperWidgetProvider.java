package com.training.imagewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class FlipperWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent serviceIntent = new Intent(context, FlipperWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.flipper_widget_layout);
            views.setRemoteAdapter(R.id.flipper_view, serviceIntent);

            // Configure flip buttons
            Intent prevIntent = new Intent(context, FlipperWidgetProvider.class);
            prevIntent.setAction("PREVIOUS_ITEM");
            PendingIntent prevPendingIntent = PendingIntent.getBroadcast(context, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.prev_button, prevPendingIntent);

            Intent nextIntent = new Intent(context, FlipperWidgetProvider.class);
            nextIntent.setAction("NEXT_ITEM");
            PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.next_button, nextPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // Handle the flip button clicks
        if("PREVIOUS_ITEM".equals(intent.getAction())) {
            // Show the previous item
        } else if("NEXT_ITEM".equals(intent.getAction())) {
            // Show the next item
        }
    }
}

