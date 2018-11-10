package com.example.vege.moviedb_ad172516.fragments.toprated;

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
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.genre.OnGetGenresCallBack;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.movie.OnGetMovieCallBack;
import com.example.vege.moviedb_ad172516.models.movie.PopularMoviesRepository;
import com.example.vege.moviedb_ad172516.models.movie.TopRatedMoviesRepository;

import java.util.List;

public class TopRatedMoviesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private static TopRatedMoviesRepository topRatedMoviesRepository;

    private static Bundle mBundleRecyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getActivity().findViewById(R.id.rvTopRatedMovies);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //recyclerview
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.spanCount)));

        topRatedMoviesRepository = TopRatedMoviesRepository.getInstance();

        getTopRatedMoviesGenres();
    }

    @Override
    public void onResume() {
        super.onResume();

        //savedInstanceState
        if (mBundleRecyclerView != null) {
            Parcelable listState = mBundleRecyclerView.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }

    }

    private void getTopRatedMoviesGenres() {

        topRatedMoviesRepository.getTopRatedMoviesGenres(new OnGetGenresCallBack() {
            @Override
            public void OnSuccess(List<Genre> genres) {
                setTopRatedMoviesRepository(genres);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setTopRatedMoviesRepository(final List<Genre> genres) {

        //sobreescribimos los metodos para llenar la recyclerview
        topRatedMoviesRepository.getMovies(new OnGetMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mMoviesAdapter = new MoviesAdapter(movies, genres, getContext());
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
