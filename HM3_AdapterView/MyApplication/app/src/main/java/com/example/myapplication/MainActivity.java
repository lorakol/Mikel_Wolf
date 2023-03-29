package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {

    Spinner viewSpinner, adapterSpinner;
    ListView listView;
    GridView gridView;
    String[] arrTitles ={
            "Sabiha Franco","Piers Thorpe", "Jak Lam",
            "Marcus Bloom","Humza Stein","Sameera Peck",
            "Asmir Phan","Nikodem Kane","Mandeep Oconnor",
            "Keiren Broughton",
    };
    String[] arrDates ={
            "10/10/1995","11/11/1997","1/15/1998",
            "12/2/2005","12/26/2007","12/10/2008",
            "9/2/2009","12/2/2015","12/5/2016",
            "9/14/2018"
    };
    Integer[] arrImages = {
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,
            R.drawable.image_4,R.drawable.image_5,R.drawable.image_6,
            R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,
            R.drawable.image_10
    };
    ArrayAdapter<String> arrayAdapter;
    MyBaseAdapter baseAdapter;
    MySimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSpinner = (Spinner) findViewById(R.id.spinner_view);
        adapterSpinner = (Spinner) findViewById(R.id.spinner_adapter);
        listView = (ListView)findViewById(R.id.listview_obj);
        gridView = (GridView)findViewById(R.id.gridview_obj);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrTitles);
        simpleAdapter = new MySimpleAdapter(this, arrTitles, arrDates);
        baseAdapter = new MyBaseAdapter(this, arrTitles, arrDates, arrImages);
        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    listView.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);
                }
                else{
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        adapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    listView.setAdapter(arrayAdapter);
                    gridView.setAdapter(arrayAdapter);
                }
                else if(position == 1){
                    listView.setAdapter(simpleAdapter);
                    gridView.setAdapter(simpleAdapter);
                }
                else{
                    listView.setAdapter(baseAdapter);
                    gridView.setAdapter(baseAdapter);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        listView.setOnItemClickListener(this);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(arrTitles[i]);
        builder.setIcon(arrImages[i]);
        builder.setMessage(arrDates[i]);
        builder.setCancelable(true);
        builder.setNegativeButton("Close", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}