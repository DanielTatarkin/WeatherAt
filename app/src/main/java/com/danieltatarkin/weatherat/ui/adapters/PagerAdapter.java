package com.danieltatarkin.weatherat.ui.adapters;

import com.danieltatarkin.weatherat.ui.fragments.HourlyFragment;
import com.danieltatarkin.weatherat.ui.fragments.NowFragment;
import com.danieltatarkin.weatherat.ui.fragments.WeekFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new HourlyFragment();
            case 1: return new NowFragment();
            case 2: return new WeekFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
