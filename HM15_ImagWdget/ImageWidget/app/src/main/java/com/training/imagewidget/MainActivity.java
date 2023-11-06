package com.training.imagewidget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ImageAdapter imageAdapter;
    MediaObserver mMediaObserver;

    private static int REQUEST_STORAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.imageGridView);
        Button refreshButton = findViewById(R.id.refreshButton);

        loadImages();

        refreshButton.setOnClickListener(v -> loadImages());

        Handler handler = new Handler();
        mMediaObserver = new MediaObserver(handler, this);
        getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                mMediaObserver);
    }

    private void loadImages() {

        // Use ContentResolver to get MediaStore images
        String[] projection = {MediaStore.Images.Media._ID};
        Uri curUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            curUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        Cursor cursor = getContentResolver().query(curUri,
                projection,
                null,
                null,
                null);

        ArrayList<Uri> imageUris = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long imageId = cursor.getLong(0);
                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
                imageUris.add(imageUri);
            }
            cursor.close();
        }

        imageAdapter = new ImageAdapter(this, imageUris);

        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Uri imageUri = imageAdapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(imageUri, "image/*");
            startActivity(intent);
        });

        checkPermission(READ_EXTERNAL_STORAGE, REQUEST_STORAGE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getContentResolver().unregisterContentObserver(mMediaObserver);
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
            //requestPermissionLauncher
        }
        else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_STORAGE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
