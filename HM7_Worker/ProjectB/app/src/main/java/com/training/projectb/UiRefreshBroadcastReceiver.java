package com.training.projectb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import java.util.List;

public class UiRefreshBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_REFRESH_UI = "com.training.projectb.ACTION_REFRESH_UI";
    private ArrayAdapter<String> adapter;
    public UiRefreshBroadcastReceiver(){

    }
    public UiRefreshBroadcastReceiver(ArrayAdapter<String> adapter){
        this.adapter = adapter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // Reload the UI to show the newly saved article
        if (intent != null && intent.getAction() != null && intent.getAction().equals(ACTION_REFRESH_UI)){
            List<String> data =  MainActivity.dbHelper.getArticles();
            adapter.addAll(data);
            adapter.notifyDataSetChanged();
        }

    }
}
