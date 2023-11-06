package com.training.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    private TextView etName, etEmail, etPhone;
    private ImageView imageView;
    private String imagepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        imageView = findViewById(R.id.image);

        loadform();
    }

    private void loadform(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String strName = sharedPreferences.getString("name", "");
        String strEmail = sharedPreferences.getString("email", "");
        String strPhone = sharedPreferences.getString("phone", "");
        imagepath = sharedPreferences.getString("image", "");
        etName.setText("Name: " + strName);
        etEmail.setText("Email: " + strEmail);
        etPhone.setText("Phone: " + strPhone);
        try{
            Intent intent = getIntent();
            Bitmap loadbit = intent.getParcelableExtra("bitmap");
            imageView.setImageBitmap(loadbit);
        }catch (Exception e){

        }
    }
}