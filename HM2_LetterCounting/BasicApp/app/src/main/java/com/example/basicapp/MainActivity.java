package com.example.basicapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.basicapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editAdd;
    Button btnAdd, btnView;
    TextView txtAverage, txtMedian;
    NumberPicker numberPicker;
    ArrayList<String> wordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editAdd = (EditText) findViewById(R.id.edit_addition);
        btnAdd = (Button) findViewById(R.id.button_addition);
        btnView = (Button) findViewById(R.id.button_view);
        txtAverage = (TextView) findViewById(R.id.txt_average);
        txtMedian = (TextView) findViewById(R.id.txt_median);
        numberPicker = (NumberPicker) findViewById(R.id.numberpicker);
        numberPicker.setMinValue(0);
        wordList = new ArrayList<>();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWord();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewWord();
            }
        });
    }

    private void addWord(){
        String strAdd = editAdd.getText().toString().trim();
        if(strAdd.equals("")){
            Toast.makeText(this, "Error: No Value Found in Text Field.", Toast.LENGTH_SHORT).show();
            return;
        }
        wordList.add(strAdd);
        editAdd.setText("");

        showWordList();
    }

    private void removeWord(){
        wordList.remove(numberPicker.getValue());
        showWordList();
    }

    private void showWordList(){
        ArrayList<Integer> lengthList = new ArrayList<>();
        float median;
        float sumLength = 0;
        for(String str : wordList){
            lengthList.add(str.length());
            sumLength += str.length();
        }
        int nlen = wordList.size();
        float average = sumLength/nlen;
        if (nlen % 2 == 1)
            median = lengthList.get((nlen + 1) / 2 - 1);
        else {
            float lower = lengthList.get(nlen / 2 - 1);
            float upper = lengthList.get(nlen / 2);

            median = (lower + upper) / 2;
        }
        txtAverage.setText(String.format("Average word length:   %.2f", average));
        txtMedian.setText(String.format("Median word length:   %.2f", median));
        if(wordList.size() > 0)
            numberPicker.setMaxValue(wordList.size()-1);
        else
            numberPicker.setMaxValue(0);


    }

    private void viewWord(){
        int nIndex = numberPicker.getValue();
        String selectWord = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Selected Word!");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", null);
        if(wordList.isEmpty()){
            selectWord = "The word list is empty";

        }
        else{
            selectWord = wordList.get(nIndex);

            builder.setPositiveButton("Remove", (DialogInterface.OnClickListener) (dialog, which) -> {
                removeWord();
            });
        }
        builder.setMessage(selectWord);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}