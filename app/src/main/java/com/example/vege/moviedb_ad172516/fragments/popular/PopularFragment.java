package com.example.vege.moviedb_ad172516.fragments.popular;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.PopularViewPagerAdapter;

public class PopularFragment extends Fragment {

    public static TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PopularViewPagerAdapter mViewPagerAdapter;
    private String KEY_VIEWPAGER_STATE = "viewpager_state";
    private String KEY_TAB_POSITION = "tab_position";
    private Bundle mBundleViewPager, mBundleTabPosition;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTabLayout = getActivity().findViewById(R.id.popularTabLayout);
        mViewPager = getActivity().findViewById(R.id.popularViewPager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createTabs();
    }

    @Override
    public void onResume() {
        super.onResume();

        //savedInstanceState
        if (mBundleViewPager != null) {
            Parcelable viewpagerState = mBundleViewPager.getParcelable(KEY_VIEWPAGER_STATE);
            mViewPager.onRestoreInstanceState(viewpagerState);

        }

        if (mBundleTabPosition != null) {
            int tabPosition = mBundleTabPosition.getInt(KEY_TAB_POSITION);
            mTabLayout.getTabAt(tabPosition).select();
        }

    }

    private void createTabs() {

        //vpager
        mViewPagerAdapter = new PopularViewPagerAdapter(getChildFragmentManager());//childfragmentmanager
        mViewPagerAdapter.addPopularFragment(new PopularMoviesFragment(), "Películas");
        mViewPagerAdapter.addPopularFragment(new PopularTVShowsFragment(), "Series de TV");
        mViewPager.setAdapter(mViewPagerAdapter);

        //tablayout
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //save viewpager instance state
        mBundleViewPager = new Bundle();
        Parcelable viewpagerState = mViewPager.onSaveInstanceState();
        mBundleViewPager.putParcelable(KEY_VIEWPAGER_STATE, viewpagerState);

        mBundleTabPosition = new Bundle();
        int tabPosition = mTabLayout.getSelectedTabPosition();
        mBundleTabPosition.putInt(KEY_TAB_POSITION, tabPosition);

    }
}
