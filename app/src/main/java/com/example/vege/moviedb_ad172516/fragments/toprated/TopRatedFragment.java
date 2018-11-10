package com.example.vege.moviedb_ad172516.fragments.toprated;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.PopularViewPagerAdapter;
import com.example.vege.moviedb_ad172516.adapters.TopRatedViewPagerAdapter;
import com.example.vege.moviedb_ad172516.fragments.popular.PopularMoviesFragment;
import com.example.vege.moviedb_ad172516.fragments.popular.PopularTVShowsFragment;

public class TopRatedFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TopRatedViewPagerAdapter mViewPagerAdapter;
    private String KEY_VIEWPAGER_STATE = "viewpager_state";
    private Bundle mBundleViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toprated, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTabLayout = getActivity().findViewById(R.id.topratedTabLayout);
        mViewPager = getActivity().findViewById(R.id.topratedViewPager);

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
    }

    private void createTabs() {

        //vpager
        mViewPagerAdapter = new TopRatedViewPagerAdapter(getChildFragmentManager());//childfragmentmanager
        mViewPagerAdapter.addTopRatedFragment(new TopRatedMoviesFragment(), "Películas");
        mViewPagerAdapter.addTopRatedFragment(new TopRatedTVShowsFragment(), "Series de TV");
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

    }
}
