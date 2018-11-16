package com.example.vege.moviedb_ad172516.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.MoviesAdapter;
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.genre.OnGetGenresCallBack;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.movie.OnGetMovieCallBack;
import com.example.vege.moviedb_ad172516.models.movie.UpComingMoviesRepository;

import java.util.List;


public class UpcomingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private static UpComingMoviesRepository upComingMoviesRepository;
    private Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = getActivity().findViewById(R.id.upcomingRecyclerView);
        mToolbar = getActivity().findViewById(R.id.upcomingToolBar);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //toolbar
        //toolbar setup
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Muy pronto");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(4);
        setHasOptionsMenu(true);

        //recyclerview
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.spanCount)));

        //instancia de retrofit
        upComingMoviesRepository = UpComingMoviesRepository.getInstance();

        //obtenemos las peliculas
        getUpComingMoviesGenres();

    }

    private void getUpComingMoviesGenres() {

        upComingMoviesRepository.getUpcomingMovieGenres(new OnGetGenresCallBack() {
            @Override
            public void OnSuccess(List<Genre> genres) {
                setUpComingMoviesRepository(genres);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Network Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpComingMoviesRepository(final List<Genre> genres) {

        //sobreescribimos los metodos para llenar la recyclerview
        upComingMoviesRepository.getUpcomingMovies(new OnGetMovieCallBack() {
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

    /**Search**/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_actions_upcoming, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search_upcoming);

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
                mMoviesAdapter.getFilter().filter(s);

                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

}
