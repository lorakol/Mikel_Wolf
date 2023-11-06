package com.training.salinasmike42;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.training.salinasmike42.fragments.ListFragment;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        // Set the initial fragment (ListFragment) in the container
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ListFragment())
                    .commit();
        }
    }
    @Override
    public void onBackPressed() {
        // Check if the back stack contains at least one fragment
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Pop the fragment from the back stack stack
            getSupportFragmentManager().popBackStack();
        } else {
            // If no more fragments are in the back stack, let the activity handle the back press
            super.onBackPressed();
        }
    }
}
