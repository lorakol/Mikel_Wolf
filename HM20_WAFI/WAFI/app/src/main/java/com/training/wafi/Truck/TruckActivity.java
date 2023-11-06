package com.training.wafi.Truck;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.training.wafi.Home.MainActivity;
import com.training.wafi.R;

public class TruckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TruckActivity", "onCreate is called");
        setContentView(R.layout.activity_truck);

        displayInventoryFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_truck) {
                    // Handle Home selection
                    return true;
                } else if (id == R.id.navigation_home) {
                    startActivity(new Intent(TruckActivity.this, MainActivity.class));
                    return true;
                } else if (id == R.id.navigation_stock) {
                    startActivity(new Intent(TruckActivity.this, StockActivity.class));
                    return true;
                } else if (id == R.id.navigation_settings) {
                    startActivity(new Intent(TruckActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Find the btnAdd button and set the OnClickListener
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AddItemActivity
                Intent intent = new Intent(TruckActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

    }

    private void displayInventoryFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        InventoryFragment inventoryFragment = new InventoryFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, inventoryFragment);
        fragmentTransaction.commit();
    }
}
