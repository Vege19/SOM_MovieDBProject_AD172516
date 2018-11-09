package com.example.vege.moviedb_ad172516.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PopularViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> popularFragments = new ArrayList<>();
    private List<String> popularFragmentsTitle = new ArrayList<>();

    public PopularViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return popularFragments.get(position);
    }

    @Override
    public int getCount() {
        return popularFragmentsTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return popularFragmentsTitle.get(position);
    }

    public void addPopularFragment(Fragment fragment, String title) {
        popularFragments.add(fragment);
        popularFragmentsTitle.add(title);
    }

}
