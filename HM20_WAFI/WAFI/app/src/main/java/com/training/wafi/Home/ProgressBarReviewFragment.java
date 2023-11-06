package com.training.wafi.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.training.wafi.R;

import java.util.List;

public class ProgressBarReviewFragment extends Fragment {

    private List<Integer> progressList;

    public ProgressBarReviewFragment(List<Integer> progressList) {
        this.progressList = progressList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_bar_review, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.progressBarList);

        for (Integer progressValue : progressList) {
            View progressBarView = inflater.inflate(R.layout.single_progress_bar, container, false);
            ProgressBar progressBar = progressBarView.findViewById(R.id.progressBar);
            TextView description = progressBarView.findViewById(R.id.description);

            progressBar.setProgress(progressValue);
            description.setText("Progress: " + progressValue + "%");

            linearLayout.addView(progressBarView);
        }

        return view;
    }
}
