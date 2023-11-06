package com.training.projecta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ImageUpdateReceiver extends BroadcastReceiver {

    private UpdateImageFunction updateImageFunction;

    public ImageUpdateReceiver(UpdateImageFunction updateImageFunction) {
        this.updateImageFunction = updateImageFunction;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String imagePath = intent.getStringExtra("imagePath");
        if (imagePath != null) {
            updateImageFunction.updateImage(imagePath);
        }
    }

    public interface UpdateImageFunction {
        void updateImage(String imagePath);
    }
}

