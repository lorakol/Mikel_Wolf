package com.training.projecta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.transform.Result;

public class ImageWorker extends Worker {

    private static final String[] IMAGE_URLS = {
            "https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            // Add the rest of the image URLs here
    };

    public ImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull

    public Result doWork() {
        for (String imageUrl : IMAGE_URLS) {
            if (isImageAlreadyDownloaded(imageUrl)) {
                // Image already exists, send broadcast to update UI
                //sendUpdateBroadcast();
                continue;
            }

            // Image doesn't exist, download and save it
            try {
                Bitmap bitmap = downloadImage(imageUrl);
                saveImage(bitmap, getImageFileName(imageUrl));
                //sendUpdateBroadcast();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Result.success();
    }

    private boolean isImageAlreadyDownloaded(String imageUrl) {
        // Check if the image file already exists in your storage based on the image URL
        // You can use the File class to access the image file and check if it exists
        // Return true if the image file exists, false otherwise
        return false; // Replace with your implementation
    }

    private Bitmap downloadImage(String imageUrl) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private String saveImage(Bitmap bitmap, String fileName) throws IOException {
        File directory = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(directory, fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file.getAbsolutePath();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


    private String getImageFileName(String imageUrl) {
        // Extract the file name from the image URL
        // You can use methods like substring or regex to extract the file name from the URL
        // Return the file name
        return ""; // Replace with your implementation
    }

    private void sendUpdateBroadcast(String imagePath) {
        Context context = getApplicationContext();
        Intent intent = new Intent("com.example.ACTION_UPDATE_GRID");
        intent.putExtra("image_path", imagePath);
        context.sendBroadcast(intent);
    }

}
