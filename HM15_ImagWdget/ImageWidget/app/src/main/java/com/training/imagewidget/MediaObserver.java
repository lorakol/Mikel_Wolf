package com.training.imagewidget;

import android.content.ContentUris;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class MediaObserver extends ContentObserver {
    private Context mContext;

    public MediaObserver(Handler handler, Context context) {
        super(handler);
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        this.onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        // Here you can handle the update
        // Perhaps send a broadcast or directly update your UI, if applicable
        updateMediaData();
    }

    private void updateMediaData() {
        List<Uri> mediaUris = new ArrayList<>();

        // Fetch the latest images
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                null, null, null);

        if (cursor != null) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                mediaUris.add(contentUri);
            }
            cursor.close();
        }

        // Update your application's UI components with the new list of mediaUris
        // You might use something like an Adapter's notifyDataSetChanged() method if you're using a GridView
    }

}

