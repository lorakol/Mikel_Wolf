package com.training.wafi.Home;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ProgressBarAdapter extends FragmentStatePagerAdapter {

    private List<Integer> progressList;

    public ProgressBarAdapter(FragmentManager fm, List<Integer> progressList) {
        super(fm);
        this.progressList = progressList;
    }

    @Override
    public Fragment getItem(int position) {
        return ProgressBarFragment.newInstance(progressList.get(position));
    }

    @Override
    public int getCount() {
        return progressList.size();
    }
}
