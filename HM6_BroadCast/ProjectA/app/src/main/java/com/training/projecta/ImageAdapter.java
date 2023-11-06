package com.training.projecta;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<File> images;

    public ImageAdapter(Context context, List<File> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.grid_view_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.image_view);
        File imageFile = images.get(position);
        if (imageFile.exists()) {
            Uri imageUri = Uri.fromFile(imageFile);
            //Glide.with(context).load(imageUri).into(imageView);
        } else {
            imageView.setImageResource(android.R.color.transparent);
        }

        return convertView;
    }
}
