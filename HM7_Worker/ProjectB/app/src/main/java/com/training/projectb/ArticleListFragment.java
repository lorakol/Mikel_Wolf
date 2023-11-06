package com.training.projectb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.ArrayList;
import java.util.List;

public class ArticleListFragment extends Fragment {

    private static final String CHANNEL_ID = "news_channel";
    private static final CharSequence CHANNEL_NAME = "News Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel for news notifications";
    private static final String ACTION_REFRESH_UI = "com.training.projectb.ACTION_REFRESH_UI";
    private static final String ACTION_SAVE_ARTICLE = "com.training.projectb.ACTION_SAVE_ARTICLE";
    private static final int NOTIFICATION_ID = 1;

    private List<String> savedArticles;
    private ArrayAdapter<String> adapter;
    private UiRefreshBroadcastReceiver uiRefreshReceiver;
    private SaveBroadcastReceiver saveReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        // Set up the notification channel
        createNotificationChannel();

        // Initialize saved articles list
        savedArticles = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, savedArticles);

        ListView listView = view.findViewById(R.id.listview);
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
        getContext().registerReceiver(uiRefreshReceiver, intentFilter);

        // Register the save broadcast receiver
        saveReceiver = new SaveBroadcastReceiver();
        intentFilter = new IntentFilter(ACTION_SAVE_ARTICLE);
        getContext().registerReceiver(saveReceiver, intentFilter);

        // Schedule the download article worker on boot completed
        scheduleDownloadArticleWorker();

        // Other initialization code
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister the broadcast receivers
        getContext().unregisterReceiver(uiRefreshReceiver);
        getContext().unregisterReceiver(saveReceiver);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void openArticleInBrowser(String articleUrl) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
        startActivity(viewIntent);
    }

    private void scheduleDownloadArticleWorker() {
        // Create a periodic worker to download news articles every 15 minutes
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(NewsWorker.class)
                .build();

        WorkManager workManager = WorkManager.getInstance(getContext());
        workManager.enqueue(workRequest);
    }



    public class UiRefreshBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Reload the UI to show the newly saved article
            if (intent != null && intent.getAction() != null && intent.getAction().equals(ACTION_REFRESH_UI))
                adapter.notifyDataSetChanged();
        }
    }

}