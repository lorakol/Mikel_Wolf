package com.training.projecta;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GridFragment extends Fragment {

    private GridView gridView;
    private ImageAdapter imageAdapter;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        gridView = view.findViewById(R.id.gridView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the adapter for the grid view
        imageAdapter = new ImageAdapter(getContext());
        gridView.setAdapter(imageAdapter);

        // Set item click listener to open full-sized image
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imagePath = imageAdapter.getImagePath(position);
                openImage(imagePath);
            }
        });

        // Register broadcast receiver to update the grid view
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Update the grid view
                imageAdapter.notifyDataSetChanged();
                String imagePath = intent.getStringExtra("image_path");
                if (imagePath != null) {
                    imageAdapter.addImagePath(imagePath);
                }

            }
        };
        IntentFilter intentFilter = new IntentFilter("com.example.ACTION_UPDATE_GRID");
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the broadcast receiver
        getContext().unregisterReceiver(broadcastReceiver);
    }

    private void openImage(String imagePath) {
        Uri imageUri = Uri.parse(imagePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(imageUri, "image/*");
        startActivity(intent);
    }
}
