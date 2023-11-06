package com.training.maps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class FormActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private ImageView imageView;
    private Button btnSubmit;
    private String imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSubmit = findViewById(R.id.btnSubmit);
        imageView = findViewById(R.id.image);
        try{
            Intent intent = getIntent();
            imagepath = intent.getStringExtra("image");
            Bitmap loadbit = intent.getParcelableExtra("bitmap");

            imageView.setImageBitmap(loadbit);
        }catch (Exception e){

        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // You can perform further validation or save the form data to a database or server
            // For now, let's just display a toast message with the form data
            String message = "Name: " + name + "\nEmail: " + email + "\nPhone: " + phone;
            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putString("phone", phone);
            editor.putString("image", imagepath);

            editor.apply();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
}
