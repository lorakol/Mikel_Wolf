package com.example.ce04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PersonArrayAdapter extends ArrayAdapter<Person>{

    private final AppCompatActivity mContext;
    private final List<Person> mPersonList;


    public PersonArrayAdapter(AppCompatActivity context, List<Person> personList) {
        super(context, android.R.layout.simple_list_item_1, personList);
        mContext = context;
        mPersonList = personList;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //LayoutInflater inflater = mContext.getLayoutInflater();
        //View view = inflater.inflate(android.R.layout.simple_list_item_1, null,true);
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        Person person = mPersonList.get(position);
        String fullName = person.getFirstName() + " " + person.getLastName();
        textView.setText(fullName);
        return view;
    }


}

