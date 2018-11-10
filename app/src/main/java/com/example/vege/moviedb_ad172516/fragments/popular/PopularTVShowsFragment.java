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
import com.example.vege.moviedb_ad172516.adapters.TVShowsAdapter;
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.genre.OnGetGenresCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.OnGetTVShowCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.PopularTVShowsRepository;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;

import java.util.List;

public class PopularTVShowsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TVShowsAdapter mAdapter;
    private PopularTVShowsRepository popularTVShowsRepository;

    private static Bundle mBundleRecyclerView;
    private final String KEY_RECYCLER_STATE = "recycler_state";

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //recyclerview
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.spanCount)));

        //instancia de retrofit
        popularTVShowsRepository = PopularTVShowsRepository.getInstance();

        getTopRatedTVShowsGenres();
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

    private void getTopRatedTVShowsGenres() {

        popularTVShowsRepository.getPopularTVShowsGenres(new OnGetGenresCallBack() {
            @Override
            public void OnSuccess(List<Genre> genres) {
                setPopularTVShowsRepository(genres);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setPopularTVShowsRepository(final List<Genre> genres) {

        //sobreescribimos los metodos para llenar la recyclerview
        popularTVShowsRepository.getPopularTVShows(new OnGetTVShowCallBack() {
            @Override
            public void onSuccess(List<TVShow> tvShows) {
                mAdapter = new TVShowsAdapter(tvShows, genres, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError() {

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
