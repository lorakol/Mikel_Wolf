package com.training.wafi.Home;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.training.wafi.R;

public class ProgressBarFragment extends Fragment {

    private static final String ARG_PROGRESS = "arg_progress";

    public static ProgressBarFragment newInstance(int progress) {
        ProgressBarFragment fragment = new ProgressBarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PROGRESS, progress);
        fragment.setArguments(args);
        return fragment;
    }

    public ProgressBarFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int progress = getArguments().getInt("PROGRESS_KEY");
        return inflater.inflate(R.layout.fragment_progress_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = view.findViewById(R.id.pie_chart);

        if (getArguments() != null) {
            int progress = getArguments().getInt(ARG_PROGRESS, 0);
            progressBar.setProgress(progress);

            if (progress >= 79) {
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            } else if (progress >= 49) {
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFA500"))); // Orange
            } else if (progress >= 25) {
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            } else {
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            }
        }
    }
}

