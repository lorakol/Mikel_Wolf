package com.training.projecta;

import android.app.DownloadManager;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity implements ImageUpdateReceiver.UpdateImageFunction {

    private ImageUpdateReceiver imageUpdateReceiver;
    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageUpdateReceiver = new ImageUpdateReceiver(this);

        String imageUrl = "https://example.com/image.jpg";
        scheduleDownloadImage(imageUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(imageUpdateReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(imageUpdateReceiver);
    }

    private void scheduleDownloadImage(String imageUrl) {
        workManager = WorkManager.getInstance(this);
        Data inputData = new Data.Builder().putString("imageUrl", imageUrl).build();
        OneTimeWorkRequest downloadImageRequest = new OneTimeWorkRequest.Builder(DownloadWorker.class)
                .setInputData(inputData)
                .build();

        workManager.enqueue(downloadImageRequest);
        workManager.getWorkInfoByIdLiveData(downloadImageRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null && workInfo.getState().isFinished()) {
                String imagePath = workInfo.getOutputData().getString("imagePath");
                if (imagePath != null) {
                    updateImage(imagePath);
                }
            }
        });
    }

    @Override
    public void updateImage(String imagePath) {
        // Implement your UI update logic to display the downloaded image
        // E.g., load the image into an ImageView
    }
}
