package com.training.imagewidget;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

public class FlipperWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FlipperRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class FlipperRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Uri> mImageUris; // load this from the MediaStore

    public FlipperRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        // Load data from MediaStore
        String[] projection = {MediaStore.Images.Media._ID};
        Uri curUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            curUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        Cursor cursor =  mContext.getContentResolver().query(curUri,
                projection,
                null,
                null,
                null);


        if (cursor != null) {
            while (cursor.moveToNext()) {
                long imageId = cursor.getLong(0);
                Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
                mImageUris.add(imageUri);
            }
            cursor.close();
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.flipper_widget_layout);
        views.setImageViewUri(R.id.flipper_view, mImageUris.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    // Implement the other necessary methods...
}

