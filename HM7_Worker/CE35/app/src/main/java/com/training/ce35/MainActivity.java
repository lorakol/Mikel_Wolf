package com.training.ce35;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "news_channel";
    private static final CharSequence CHANNEL_NAME = "News Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel for news notifications";
    private static final String ACTION_REFRESH_UI = "your.package.name.ACTION_REFRESH_UI";
    private static final String ACTION_SAVE_ARTICLE = "your.package.name.ACTION_SAVE_ARTICLE";
    private static final int NOTIFICATION_ID = 1;

    private List<String> savedArticles;
    private ArrayAdapter<String> adapter;
    private UiRefreshBroadcastReceiver uiRefreshReceiver;
    private SaveBroadcastReceiver saveReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the notification channel
        createNotificationChannel();

        // Initialize saved articles list
        savedArticles = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedArticles);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Set up click listener for saved articles list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleUrl = savedArticles.get(position);
                openArticleInBrowser(articleUrl);
            }
        });

        // Register the UI refresh broadcast receiver
        uiRefreshReceiver = new UiRefreshBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ACTION_REFRESH_UI);
        registerReceiver(uiRefreshReceiver, intentFilter);

        // Register the save broadcast receiver
        saveReceiver = new SaveBroadcastReceiver();
        intentFilter = new IntentFilter(ACTION_SAVE_ARTICLE);
        registerReceiver(saveReceiver, intentFilter);

        // Schedule the download article worker on boot completed
        scheduleDownloadArticleWorker();

        // Other initialization code
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister the broadcast receivers
        unregisterReceiver(uiRefreshReceiver);
        unregisterReceiver(saveReceiver);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String articleTitle, String articleDescription, String articleUrl) {
        // Create an Intent with the article URL to open it in the default web browser
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                viewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Create the notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(articleTitle)
                .setContentText(articleDescription)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(articleDescription))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void openArticleInBrowser(String articleUrl) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        startActivity(viewIntent);
    }

    private void scheduleDownloadArticleWorker() {
        // Create a periodic worker to download news articles every 15 minutes
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(DownloadArticleWorker.class)
                .build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(workRequest);
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

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(workRequest);
    }

    public class UiRefreshBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Reload the UI to show the newly saved article
            adapter.notifyDataSetChanged();
        }
    }

    public class SaveBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String article = intent.getStringExtra("article");

            // Schedule the save article worker using the OneTimeWorkRequest
            scheduleSaveArticleWorker(article);
        }
    }
}
