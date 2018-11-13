package com.example.vege.moviedb_ad172516.fragments.popular;

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
import com.example.vege.moviedb_ad172516.models.movie.PopularMoviesRepository;
import com.example.vege.moviedb_ad172516.models.tvShow.OnGetTVShowCallBack;
import com.example.vege.moviedb_ad172516.models.tvShow.PopularTVShowsRepository;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;

import java.util.List;

public class PopularFragment extends Fragment {

    private RecyclerView popularRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private android.support.v7.widget.Toolbar mToolBar;
    private TVShowsAdapter mTVShowsAdapter;
    private PopularMoviesRepository popularMoviesRepository;
    private PopularTVShowsRepository popularTVShowsRepository;
    private FloatingActionButton contentFab;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        popularRecyclerView = getActivity().findViewById(R.id.rvPopular);
        mToolBar = getActivity().findViewById(R.id.popularToolBar);
        contentFab = getActivity().findViewById(R.id.fabChangePopular);
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

        //toolbar setup
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Popular · Películas");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(4);
        setHasOptionsMenu(true);

        //recyclerview
        popularRecyclerView.setHasFixedSize(true);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.spanCount)));

        //get retrofit instance
        popularMoviesRepository = PopularMoviesRepository.getInstance();
        popularTVShowsRepository = PopularTVShowsRepository.getInstance();

        getPopularMoviesAndGenres();

        //fab action
        contentFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popularRecyclerView.getAdapter() == mMoviesAdapter) {
                    getPopularTVShowsAndGenres();
                    contentFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_outline_movie_24px));
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Popular · Series de TV");
                } else if (popularRecyclerView.getAdapter() == mTVShowsAdapter) {
                    getPopularMoviesAndGenres();
                    contentFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_live_tv_white_24dp));
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Popular · Películas");
                }
            }
        });

    }

    /**MOVIES**/
    private void getPopularMoviesAndGenres() {

        popularMoviesRepository.getPopularMoviesGenres(new OnGetGenresCallBack() {
            @Override
            public void OnSuccess(List<Genre> genres) {
                getPopularMoviesRepository(genres);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getPopularMoviesRepository(final List<Genre> genres) {

        //sobreescribimos los metodos para llenar la recyclerview
        popularMoviesRepository.getMovies(new OnGetMovieCallBack() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mMoviesAdapter = new MoviesAdapter(movies, genres, getContext());
                popularRecyclerView.setAdapter(mMoviesAdapter);

            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**TV SHOWS**/
    private void getPopularTVShowsAndGenres() {

        popularTVShowsRepository.getPopularTVShowsGenres(new OnGetGenresCallBack() {
            @Override
            public void OnSuccess(List<Genre> genres) {
                getPopularTVShowsRepository(genres);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getPopularTVShowsRepository(final List<Genre> genres) {

        //sobreescribimos los metodos para llenar la recyclerview
        popularTVShowsRepository.getPopularTVShows(new OnGetTVShowCallBack() {
            @Override
            public void onSuccess(List<TVShow> tvShows) {
                mTVShowsAdapter = new TVShowsAdapter(tvShows, genres, getContext());
                popularRecyclerView.setAdapter(mTVShowsAdapter);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_actions, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //customize search area
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search in Popular");
        //Close button
        ImageView closeOption = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeOption.setImageResource(R.drawable.ic_close_white_24dp);
        //Text
        EditText et = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(getResources().getColor(R.color.transparentWhite));

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
                if (popularRecyclerView.getAdapter() == mMoviesAdapter) {
                    mMoviesAdapter.getFilter().filter(s);
                } else if (popularRecyclerView.getAdapter() == mTVShowsAdapter) {
                    mTVShowsAdapter.getFilter().filter(s);
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }
}
