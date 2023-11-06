package com.training.projecta;

import android.content.Context;
import android.content.Intent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;



public class DownloadWorker extends Worker {
    private final String URL_BASE = "https://i.imgur.com/";

    private final String[] IMAGES = {

            "Df9sV7x.jpg", "nqnegVs.jpg", "JDCG1tP.jpg",

            "tUvlwvB.jpg", "2bTEbC5.jpg", "Jnqn9NJ.jpg",

            "xd2M3FF.jpg", "atWe0me.jpg", "UJROzhm.jpg",

            "4lEPonM.jpg", "vxvaFmR.jpg", "NDPbJfV.jpg",

            "ZPdoCbQ.jpg", "SX6hzar.jpg", "YDNldPb.jpg",

            "iy1FvVh.jpg", "OFKPzpB.jpg", "P0RMPwI.jpg",

            "lKrCKtM.jpg", "eXvZwlw.jpg", "zFQ6TwY.jpg",

            "mTY6vrd.jpg", "QocIraL.jpg", "VYZGZnk.jpg",

            "RVzjXTW.jpg", "1CUQgRO.jpg", "GSZbb2d.jpg",

            "IvMKTro.jpg", "oGzBLC0.jpg", "55VipC6.jpg"

    };
    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // TODO: Replace with your array of 30 image URLs


        for (String imageUrl : IMAGES) {
            imageUrl = URL_BASE + imageUrl;
            String filename = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            File file = new File(getApplicationContext().getFilesDir(), filename);

            if (file.exists()) {
                sendUpdateBroadcast(file);
            } else {
                downloadAndSaveImage(imageUrl, file);
                sendUpdateBroadcast(file);
            }
        }

        return Result.success();
    }

    private void downloadAndSaveImage(String imageUrl, File outputFile) {
        try {
            URL url = new URL(imageUrl);
            //URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendUpdateBroadcast(File file) {
        Intent updateIntent = new Intent("com.training.projecta.ACTION_UPDATE");
        updateIntent.putExtra("image_path", file.getAbsolutePath());
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(updateIntent);
    }
}
