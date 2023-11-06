package com.training.wafi.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.training.wafi.ConversationsActivity;
import com.training.wafi.R;
import com.training.wafi.Truck.ProgressData;
import com.training.wafi.Truck.SettingsActivity;
import com.training.wafi.Truck.StockActivity;
import com.training.wafi.Truck.TruckActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ProgressBarAdapter adapter;
    private ScrollView progressScrollView;
    private boolean progressBarsLoaded = false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressData progData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ProgressBar");
        udpatData();
        if(progData == null){
            progData = new ProgressData();
            progData.progress_1 = 30;
            progData.progress_2 = 90;
            progData.progress_3 = 75;
        }

        // Sample data
        List<Integer> progressData = new ArrayList<>();
        progressData.add(progData.progress_1);
        progressData.add(progData.progress_2);
        progressData.add(progData.progress_3);

        viewPager = findViewById(R.id.viewPager);
        adapter = new ProgressBarAdapter(getSupportFragmentManager(), progressData);
        viewPager.setAdapter(adapter);

        progressScrollView = findViewById(R.id.progressScrollView);
        ConstraintLayout rootLayout = findViewById(R.id.root);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    // Handle Home selection
                    return true;
                } else if (id == R.id.navigation_truck) {
                    startActivity(new Intent(MainActivity.this, TruckActivity.class));
                    return true;
                } else if (id == R.id.navigation_stock) {
                    startActivity(new Intent(MainActivity.this, StockActivity.class));
                    return true;
                } else if (id == R.id.navigation_settings) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Set onTouchListener
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int[] location = new int[2];
                    progressScrollView.getLocationOnScreen(location);
                    Rect rect = new Rect(location[0], location[1], location[0] + progressScrollView.getWidth(), location[1] + progressScrollView.getHeight());
                    if (!rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        progressScrollView.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                    }
                    v.performClick();
                }
                return false;
            }
        });

        // Set onClickListener with log statement
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log statement to indicate the click event on the rootLayout
                Log.d("MainActivity", "RootLayout clicked");
            }
        });

        Button btnReview = findViewById(R.id.btnReview);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayProgressBars();
            }
        });

        Button btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start ConversationsActivity
                Intent intent = new Intent(MainActivity.this, ConversationsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void udpatData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                progData = snapshot.getValue(ProgressData.class);

                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity.this, "data changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProgressBars() {
        LinearLayout progressBarContainer = findViewById(R.id.progressBarContainer);

        if (!progressBarsLoaded) {
            List<Pair<Integer, String>> progressData = Arrays.asList(
                    new Pair<>(progData.progress_1, "Description 1"),
                    new Pair<>(progData.progress_2, "Description 2"),
                    new Pair<>(progData.progress_3, "Description 3")
            );

            LayoutInflater inflater = LayoutInflater.from(this);
            for (Pair<Integer, String> data : progressData) {
                View progressView = inflater.inflate(R.layout.progress_item, progressBarContainer, false);

                TextView tvDescription = progressView.findViewById(R.id.tvDescription);
                ProgressBar progressBar = progressView.findViewById(R.id.progressBar);

                tvDescription.setText(data.second);
                progressBar.setProgress(data.first);

                progressBarContainer.addView(progressView);
            }
            progressBarsLoaded = true;
        }

        if (progressScrollView.getVisibility() == View.VISIBLE) {
            progressScrollView.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
        } else {
            progressScrollView.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        }
    }
}
