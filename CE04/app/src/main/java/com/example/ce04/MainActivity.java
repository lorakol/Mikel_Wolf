package com.example.ce04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private Spinner mSpinnerViewType;
    private Spinner mSpinnerAdapterType;

    private ViewSwitcher mViewSwitcher;
    private GridView mGridView;
    private ListView mListView;
    private PersonArrayAdapter mArrayAdapter;
    private PersonBaseAdapter mBaseAdapter;
    private SimpleAdapter mSimpleAdapter;
    private List<Person> mPersonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        mSpinnerViewType = findViewById(R.id.spinner1);
        mSpinnerAdapterType = findViewById(R.id.spinner2);
        mGridView = findViewById(R.id.grid_view);
        mListView = findViewById(R.id.list_view);
        mViewSwitcher = findViewById(R.id.view_switcher);
        // Set up spinners
        ArrayAdapter<CharSequence> viewTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.view_options, android.R.layout.simple_spinner_item);
        viewTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerViewType.setAdapter(viewTypeAdapter);
        mSpinnerViewType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.adapter_options, android.R.layout.simple_spinner_item);
        adapterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAdapterType.setAdapter(adapterTypeAdapter);
        mSpinnerAdapterType.setOnItemSelectedListener(this);

        // Set up person data
        mPersonList = new ArrayList<>();
        mPersonList.add(new Person("John", "Doe", "01/01/2000", ResourcesCompat.getDrawable(getResources(), R.drawable.person1, null)));
        mPersonList.add(new Person("Jane", "Doe", "02/02/2002", ResourcesCompat.getDrawable(getResources(), R.drawable.person2, null)));
        mPersonList.add(new Person("Bob", "Smith", "03/03/2003", ResourcesCompat.getDrawable(getResources(), R.drawable.person3, null)));
        mPersonList.add(new Person("Alice", "Johnson", "04/04/2004", ResourcesCompat.getDrawable(getResources(), R.drawable.person4, null)));

        // Set up initial adapter
        mArrayAdapter = new PersonArrayAdapter(this,  mPersonList);
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < mPersonList.size(); i++) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("Name", mPersonList.get(i).getFirstName() + " " + mPersonList.get(i).getLastName());
            map.put("Date", mPersonList.get(i).getBirthday());
            list.add(map);
        }
        String[] from = {"Name", "Date"};
        int to[] = {R.id.txt_name, R.id.txt_date};
        mSimpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.simple_adapter,from, to);
        mBaseAdapter = new PersonBaseAdapter(this, mPersonList);

        mListView.setAdapter(mArrayAdapter);

        // Set up item click listener
        mListView.setOnItemClickListener(this);
        mGridView.setOnItemClickListener(this);
        mViewSwitcher.showNext();
        // Set up view switcher
        //mViewSwitcher.addView();
//        mViewSwitcher.setFactory(() -> {
//            View view = new GridView(this);
//            ((GridView) view).setNumColumns(2);
//            ((GridView) view).setHorizontalSpacing(16);
//            ((GridView) view).setVerticalSpacing(16);
//            view.setPadding(16, 16, 16, 16);
//            //view.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return view;
//        });
//        int n = 0;
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mSpinnerViewType) {
            if (position == 0) { // Grid view
                mGridView.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
            } else { // List view

                mListView.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
            }
        } else if (parent == mSpinnerAdapterType) {
            if (position == 0) { // Default adapter
                mListView.setAdapter(mArrayAdapter);
                mGridView.setAdapter(mArrayAdapter);
                //mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, mPersonList);
            } else if (position == 1){ // Custom adapter
                mListView.setAdapter(mSimpleAdapter);
                mGridView.setAdapter(mSimpleAdapter);
                //PersonArrayAdapter personArrayAdapter = new PersonArrayAdapter(this, mPersonList);
                //mAdapter = personArrayAdapter.getArrayAdapter();
            }
            else{
                mListView.setAdapter(mBaseAdapter);
                mGridView.setAdapter(mBaseAdapter);
            }
            //mListView.setAdapter(mAdapter);
            //mGridView.setAdapter(mAdapter);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Person person = mPersonList.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.person_dialog, null);

        TextView nameTextView = dialogView.findViewById(R.id.dialog_name);
        TextView birthdayTextView = dialogView.findViewById(R.id.dialog_birthday);
        ImageView pictureImageView = dialogView.findViewById(R.id.dialog_picture);

        nameTextView.setText(person.getFirstName() + " " + person.getLastName());
        birthdayTextView.setText(person.getBirthday());
        pictureImageView.setImageDrawable(person.getPicture());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
