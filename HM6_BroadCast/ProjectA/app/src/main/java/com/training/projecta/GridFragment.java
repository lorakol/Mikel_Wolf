package com.training.projecta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GridFragment#} factory method to
 * create an instance of this fragment.
 */
public class GridFragment extends Fragment {

    private GridView gridView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        gridView = view.findViewById(R.id.grid_view);
        gridView.setOnItemClickListener((parent, v, position, id) -> {
            // Add click listener logic here
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_start) {
            // Start the download process
        }
        return super.onOptionsItemSelected(item);
    }
}