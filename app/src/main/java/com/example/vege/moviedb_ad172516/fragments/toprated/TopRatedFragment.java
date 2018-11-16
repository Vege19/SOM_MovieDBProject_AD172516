package com.example.vege.moviedb_ad172516.fragments.toprated;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.MoviesAdapter;
import com.example.vege.moviedb_ad172516.adapters.TVShowsAdapter;
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.genre.OnGetGenresCallBack;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.movie.OnGetMovieCallBack;
import com.example.vege.moviedb_ad172516.models.movie.TopRatedMoviesRepository;
import com.example.vege.moviedb_ad172516.models.tvShow.OnGetTVShowCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;
import com.example.vege.moviedb_ad172516.models.tvShow.TopRatedTVShowsRepository;

import java.util.List;

public class TopRatedFragment extends Fragment {

    private RecyclerView topRatedRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private TVShowsAdapter mTVShowsAdapter;
    private TopRatedMoviesRepository topRatedMoviesRepository;
    private TopRatedTVShowsRepository topRatedTVShowsRepository;
    private FloatingActionButton contentFab;
    private android.support.v7.widget.Toolbar mToolBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_toprated, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topRatedRecyclerView = getActivity().findViewById(R.id.rvTopRated);
        contentFab = getActivity().findViewById(R.id.fabChangeTopRated);
        mToolBar = getActivity().findViewById(R.id.topratedToolBar);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //toolbar support
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mejores · Películas");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(4);
        setHasOptionsMenu(true);

        //recyclerview
        topRatedRecyclerView.setHasFixedSize(true);
        topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        topRatedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.spanCount)));

        //retrofit
        topRatedMoviesRepository = TopRatedMoviesRepository.getInstance();
        topRatedTVShowsRepository = TopRatedTVShowsRepository.getInstance();

        getTopRatedMoviesAndGenres();

        contentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (topRatedRecyclerView.getAdapter() == mMoviesAdapter) {
                    getTopRatedTVShowsAndGenres();
                    contentFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_outline_movie_24px));
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mejores · Series de TV");
                } else if (topRatedRecyclerView.getAdapter() == mTVShowsAdapter) {
                    getTopRatedMoviesAndGenres();
                    contentFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_live_tv_white_24dp));
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Mejores · Películas");
                }
            }
        });

    }

    /**MOVIES**/
    private void getTopRatedMoviesAndGenres() {

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
                topRatedRecyclerView.setAdapter(mMoviesAdapter);

            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getTopRatedTVShowsAndGenres() {

        topRatedTVShowsRepository.getTopRatedTVShowsGenres(new OnGetGenresCallBack() {
            @Override
            public void OnSuccess(List<Genre> genres) {
                setTopRatedTVShowsRepository(genres);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**TV SHOWS**/
    void setTopRatedTVShowsRepository(final List<Genre> genres) {

        //sobreescribimos los metodos para llenar la recyclerview
        topRatedTVShowsRepository.getTopRatedTVShows(new OnGetTVShowCallBack() {
            @Override
            public void onSuccess(List<TVShow> tvShows) {
                mTVShowsAdapter = new TVShowsAdapter(tvShows, genres, getContext());
                topRatedRecyclerView.setAdapter(mTVShowsAdapter);

            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Search in toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_actions_toprated, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_toprated);

        //customize search area
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search in Popular");

        //searchview setup
        android.support.v7.widget.SearchView search = (android.support.v7.widget.SearchView) searchItem.getActionView();

        search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //si muestra peliculas filtra peliculas, si muestra series filtra series
                if (topRatedRecyclerView.getAdapter() == mMoviesAdapter) {
                    mMoviesAdapter.getFilter().filter(s);
                } else if (topRatedRecyclerView.getAdapter() == mTVShowsAdapter) {
                    mTVShowsAdapter.getFilter().filter(s);
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

}
