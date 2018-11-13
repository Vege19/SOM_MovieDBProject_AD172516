package com.example.vege.moviedb_ad172516.fragments.popular;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.MoviesAdapter;
import com.example.vege.moviedb_ad172516.adapters.PopularViewPagerAdapter;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        popularRecyclerView = getActivity().findViewById(R.id.rvPopular);
        mToolBar = getActivity().findViewById(R.id.popularToolBar);
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
        setHasOptionsMenu(true);


        //recyclerview
        popularRecyclerView.setHasFixedSize(true);
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.spanCount)));

        //get retrofit instance
        popularMoviesRepository = PopularMoviesRepository.getInstance();
        popularTVShowsRepository = PopularTVShowsRepository.getInstance();

        getPopularMoviesAndGenres();


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


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //si la recycler esta mostrando peliculas, mostrara series y viceversa
        switch (item.getItemId()) {
            case R.id.action_change:
                if (popularRecyclerView.getAdapter() == mMoviesAdapter) {
                    getPopularTVShowsAndGenres();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_local_movies_white_24dp));
                } else if (popularRecyclerView.getAdapter() == mTVShowsAdapter){
                    getPopularMoviesAndGenres();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_tv_white_24dp));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
