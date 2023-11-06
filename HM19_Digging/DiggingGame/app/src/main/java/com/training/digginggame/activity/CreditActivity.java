package com.training.digginggame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.training.digginggame.R;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}