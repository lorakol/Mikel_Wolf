package com.training.c;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private String[] imagePaths;
    private ArrayList<String> imagePaths;

    public ImageAdapter(Context context) {
        this.context = context;
        this.imagePaths = new ArrayList<>();
    }

    public void addImagePath(String imagePath) {
        imagePaths.add(imagePath);
    }


    @Override
    public int getCount() {
        return imagePaths.length;
    }

    @Override
    public Object getItem(int position) {
        return imagePaths[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Load and set the thumbnail image
        String imagePath = imagePaths.get(position);
        Bitmap thumbnail = decodeSampledBitmap(imagePath, 100, 100);
        ImageView imageView = null;
        imageView.setImageBitmap(thumbnail);

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        // Load and set the thumbnail image
        String imagePath = imagePaths[position];
        Bitmap thumbnail = decodeSampledBitmap(imagePath, 100, 100);
        imageView.setImageBitmap(thumbnail);

        return imageView;
    }

    private String[] getImagePaths() {
        // Get the array of image file names from your storage
        // This is where you should retrieve the image file names based on the downloaded images
        // You can use the File class to access the images based on their file names
        // Return an array of file paths, URIs, or any representation you choose to use for the images
        return new String[0]; // Replace with your implementation
    }

    public void addImagePath(String imagePath) {
        imagePaths.add(imagePath);
    }


    private Bitmap decodeSampledBitmap(String imagePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
