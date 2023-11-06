package com.training.projecta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private ImageAdapter imageAdapter;
    public MyBroadcastReceiver(){

    }
    public MyBroadcastReceiver(ImageAdapter adapter){
        imageAdapter = adapter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {


        // Update the grid view
        imageAdapter.notifyDataSetChanged();

        String imagePath = intent.getStringExtra("image_path");
        Log.e("download", imagePath);
        if (imagePath != null) {
            imageAdapter.addImagePath(imagePath);
        }
    }
}
