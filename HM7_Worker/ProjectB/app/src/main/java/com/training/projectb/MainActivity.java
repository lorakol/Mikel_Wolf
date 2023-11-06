package com.training.projectb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    public static ArticleDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ArticleDBHelper(this);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new ArticleListFragment())
                    .commit();
        }
    }
}