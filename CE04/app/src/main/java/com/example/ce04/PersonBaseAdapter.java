package com.example.ce04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PersonBaseAdapter extends BaseAdapter {

    private final AppCompatActivity mContext;
    private final List<Person> mPersonList;
    public PersonBaseAdapter(AppCompatActivity context, List<Person> personList){
        mContext = context;
        mPersonList = personList;
    }
    @Override
    public int getCount() {
        return mPersonList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPersonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.base_adapter, null);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image_src);
        TextView nameText = (TextView) rowView.findViewById(R.id.txt_name);
        TextView dateText = (TextView) rowView.findViewById(R.id.txt_date);

        Person p = mPersonList.get(i);
        nameText.setText(p.getFirstName() + " " + p.getLastName());
        imageView.setImageDrawable(p.getPicture());
        dateText.setText(p.getBirthday());
        return rowView;
    }
}
