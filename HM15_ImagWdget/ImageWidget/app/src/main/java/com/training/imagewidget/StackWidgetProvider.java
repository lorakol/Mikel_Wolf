package com.training.imagewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class StackWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent serviceIntent = new Intent(context, StackWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stack_widget_layout);
            views.setRemoteAdapter(R.id.stack_view, serviceIntent);
            // More configurations here...


            //Intent viewIntent = new Intent(context, SomeActivity.class);
            //PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
            //views.setPendingIntentTemplate(R.id.stack_view, viewPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
