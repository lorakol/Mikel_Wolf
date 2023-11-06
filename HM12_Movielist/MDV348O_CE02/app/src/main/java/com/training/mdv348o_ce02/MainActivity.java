package com.training.mdv348o_ce02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;


import android.os.Bundle;


import com.training.mdv348o_ce02.MainFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Load the MainFragment into the container
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, mainFragment)
                    .commit();
        }
    }
}
