package com.training.imagewidget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<Uri> imageList;

    public ImageAdapter(Context context, List<Uri> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Uri getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Load and display the image using your preferred image loading library (e.g., Picasso, Glide)
        //String imageUrl = imageList.get(position);
        holder.imageView.setImageURI(imageList.get(position));
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(imageList.get(position), "image/*");
//                context.startActivity(intent);
//            }
//        });
        // Glide.with(context).load(imageUrl).into(holder.imageView);
        // Picasso.get().load(imageUrl).into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
