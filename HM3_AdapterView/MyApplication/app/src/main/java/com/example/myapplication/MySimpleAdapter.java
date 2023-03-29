package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MySimpleAdapter extends ArrayAdapter<String> {
    private final AppCompatActivity context;
    private final String[] arrTitle;
    private final String[] arrDates;

    public MySimpleAdapter(AppCompatActivity context, String[] arrTitle, String[] arrDates) {
        super(context, R.layout.simple_view, arrTitle);
        this.context = context;
        this.arrTitle = arrTitle;
        this.arrDates = arrDates;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.simple_view, null,true);

        TextView nameText = (TextView) rowView.findViewById(R.id.txt_name);
        TextView dateText = (TextView) rowView.findViewById(R.id.txt_date);

        nameText.setText(arrTitle[position]);
        dateText.setText(arrDates[position]);

        return rowView;

    };
}
