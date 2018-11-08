package com.example.vege.moviedb_ad172516.fragments.popular;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.MoviesAdapter;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.movie.OnGetMovieCallBack;
import com.example.vege.moviedb_ad172516.models.movie.PopularMoviesRepository;

import java.util.List;

public class PopularMoviesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private static PopularMoviesRepository popularMoviesRepository;

    private static Bundle mBundleRecyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getActivity().findViewById(R.id.rvPopularMovies);

    }

    @Override
    public void onResume() {
        super.onResume();

        //recyclerview
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //obtenemos las peliculas
        setPopularMoviesRepository();

        //savedInstanceState
        if (mBundleRecyclerView != null) {
            Parcelable listState = mBundleRecyclerView.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }

    }

    void setPopularMoviesRepository() {

        //instancia de retrofit
        popularMoviesRepository = PopularMoviesRepository.getInstance();

        //sobreescribimos los metodos para llenar la recyclerview
        popularMoviesRepository.getMovies(new OnGetMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mMoviesAdapter = new MoviesAdapter(movies, getContext());
                mRecyclerView.setAdapter(mMoviesAdapter);

            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        //save recyclewview state
        mBundleRecyclerView = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerView.putParcelable(KEY_RECYCLER_STATE, listState);
    }
}
