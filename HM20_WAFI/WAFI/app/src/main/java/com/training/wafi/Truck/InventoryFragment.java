package com.training.wafi.Truck;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.training.wafi.FirebaseHelper;
import com.training.wafi.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    private RecyclerView inventoryRecyclerView;
    private InventoryAdapter adapter;
    private FirebaseHelper firebaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);

        inventoryRecyclerView = view.findViewById(R.id.inventoryRecyclerView);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter and set it to the RecyclerView
        adapter = new InventoryAdapter(new ArrayList<>());
        inventoryRecyclerView.setAdapter(adapter);

        firebaseHelper = new FirebaseHelper(getContext());
        firebaseHelper.loadData(new FirebaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Item> items) {
                // Update the adapter’s data and notify it of changes
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
                for (Item item : items) {
                    Log.d("FirebaseHelper", "Item Name: " + item.getName());
                }
            }

            @Override
            public void DataIsLoaded(List<Item> items, List<String> keys) {
                // Handle the case where the keys are not null if needed.
            }
        });

        return view;
    }
}
