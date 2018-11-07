package com.example.vege.moviedb_ad172516.fragments.popular;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.Error;
import com.example.vege.moviedb_ad172516.adapters.TVShowsAdapter;
import com.example.vege.moviedb_ad172516.models.tvShow.OnGetTVShowCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.PopularTVShowsRepository;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;

import java.util.List;

public class PopularTVShowsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TVShowsAdapter mAdapter;
    private PopularTVShowsRepository popularTVShowsRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular_tvshows, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getActivity().findViewById(R.id.rvPopularTVShows);

    }

    @Override
    public void onResume() {
        super.onResume();

        //recyclerview
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setPopularTVShowsRepository();

    }

    void setPopularTVShowsRepository() {

        //instancia de retrofit
        popularTVShowsRepository = PopularTVShowsRepository.getInstance();

        //sobreescribimos los metodos para llenar la recyclerview
        popularTVShowsRepository.getPopularTVShows(new OnGetTVShowCallBack() {
            @Override
            public void onSuccess(List<TVShow> tvShows) {
                mAdapter = new TVShowsAdapter(tvShows, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError() {
                mRecyclerView.setAdapter(new Error());

            }
        });

    }
}
