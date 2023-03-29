package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyBaseAdapter extends ArrayAdapter<String> {

    private final AppCompatActivity context;
    private final String[] arrTitle;
    private final String[] arrDates;
    private final Integer[] arrImages;

    public MyBaseAdapter(AppCompatActivity context, String[] arrTitle, String[] arrDates, Integer[] arrImages) {
        super(context, R.layout.base_view, arrTitle);
        this.context = context;
        this.arrTitle = arrTitle;
        this.arrDates = arrDates;
        this.arrImages = arrImages;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.base_view, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.image_src);
        TextView nameText = (TextView) rowView.findViewById(R.id.txt_name);
        TextView dateText = (TextView) rowView.findViewById(R.id.txt_date);

        nameText.setText(arrTitle[position]);
        imageView.setImageResource(arrImages[position]);
        dateText.setText(arrDates[position]);

        return rowView;

    };
}
