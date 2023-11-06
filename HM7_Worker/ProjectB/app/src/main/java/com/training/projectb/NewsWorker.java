package com.training.projectb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NewsWorker extends Worker {

    private static final String CHANNEL_ID = "news_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int NOTIFICATION_ACTION_REQUEST_CODE = 2;
    private static final String ACTION_SAVE_ARTICLE = "save_article";
    private final String BASE_URL = "https://edition.cnn.com/";

    public NewsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Simulate downloading a news feed
        // Replace this with your own logic to download the news feed
        // and select a random article
        String randomArticle = getRandomArticle();

        // Display a notification with the article details
        showNotification(randomArticle);

        // Return the result of the work
        return Result.success();
    }

    private String getRandomArticle() {
        // Replace this with your own logic to select a random article from the news feed
        // Here, we're just returning a random article title for demonstration purposes
        String[] articles = {"us crime-and-justice", "weather", "us", "specials us energy-and-environment", "us space-science"};
        int randomIndex = new Random().nextInt(articles.length);
        return articles[randomIndex];
    }

    private void showNotification(String article) {
        // Create a notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "News Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getNotificationManager();
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New Article")
                .setContentText(article)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getViewArticlePendingIntent(article))
                .addAction(R.drawable.ic_save, "Save", getSaveArticlePendingIntent(article))
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private PendingIntent getViewArticlePendingIntent(String article) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setData(Uri.parse(getArticleUrl(article)));
        return PendingIntent.getActivity(
                getApplicationContext(),
                0,
                viewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private PendingIntent getSaveArticlePendingIntent(String article) {
        Intent saveIntent = new Intent(getApplicationContext(), SaveBroadcastReceiver.class);
        saveIntent.setAction(ACTION_SAVE_ARTICLE);
        saveIntent.putExtra("article", article);
        return PendingIntent.getBroadcast(
                getApplicationContext(),
                NOTIFICATION_ACTION_REQUEST_CODE,
                saveIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private String getArticleUrl(String article) {
        // Replace this with your own logic to get the URL for the selected article
        // based on the article title
        return BASE_URL + article.replace(" ", "/");
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void schedulePeriodicWork(Context context) {
        // Create a periodic work request to run every 15 minutes
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                NewsWorker.class,
                15,
                TimeUnit.MINUTES
        ).build();

        // Enqueue the work request
        WorkManager.getInstance(context).enqueue(workRequest);
    }
}

