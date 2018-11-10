package com.example.vege.moviedb_ad172516.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TopRatedViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> topratedFragments = new ArrayList<>();
    private List<String> topratedFragmentsTitle = new ArrayList<>();

    public TopRatedViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return topratedFragments.get(position);
    }

    @Override
    public int getCount() {
        return topratedFragmentsTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return topratedFragmentsTitle.get(position);
    }

    public void addTopRatedFragment(Fragment fragment, String title) {
        topratedFragments.add(fragment);
        topratedFragmentsTitle.add(title);
    }

}
