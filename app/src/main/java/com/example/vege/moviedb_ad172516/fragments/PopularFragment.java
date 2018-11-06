package com.example.vege.moviedb_ad172516.fragments;

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
import com.example.vege.moviedb_ad172516.adapters.PopularAdapter;
import com.example.vege.moviedb_ad172516.models.Movie;
import com.example.vege.moviedb_ad172516.models.MovieRepository;
import com.example.vege.moviedb_ad172516.models.OnGetMovieCallBack;
import com.example.vege.moviedb_ad172516.network.MovieDBService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PopularAdapter mAdapter;
    private MovieRepository movieRepository;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getActivity().findViewById(R.id.rvPopularMovies);

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

        //instancia de retrofit
        movieRepository = MovieRepository.getInstance();

        //recyclerview
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //sobreescribimos los metodos para llenar la recyclerview
        movieRepository.getMovies(new OnGetMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mAdapter = new PopularAdapter(movies, getContext());
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onError() {
                mRecyclerView.setAdapter(new Error());

            }
        });

    }

}
