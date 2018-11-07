package com.example.vege.moviedb_ad172516.fragments.popular;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.Error;
import com.example.vege.moviedb_ad172516.adapters.MoviesAdapter;
import com.example.vege.moviedb_ad172516.adapters.PopularViewPagerAdapter;
import com.example.vege.moviedb_ad172516.adapters.TVShowsAdapter;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.movie.PopularMoviesRepository;
import com.example.vege.moviedb_ad172516.models.movie.OnGetMovieCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.OnGetTVShowCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.PopularTVShowsRepository;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;

import java.util.List;

public class PopularFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PopularViewPagerAdapter mViewPagerAdapter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();

        createTabs();

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

}
