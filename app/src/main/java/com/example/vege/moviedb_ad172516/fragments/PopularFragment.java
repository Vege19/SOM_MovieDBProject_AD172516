package com.example.vege.moviedb_ad172516.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.PopularAdapter;
import com.example.vege.moviedb_ad172516.models.ApiService;
import com.example.vege.moviedb_ad172516.models.Movie;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PopularFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PopularAdapter mAdapter;

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

        //Recyclerview config
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mAdapter = new PopularAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        getPopularMovies();
    }

    //peticion de los datos
    private void getPopularMovies() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "888eed6d5b3879fea3cf535a3b85d827");
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        ApiService service = restAdapter.create(ApiService.class);
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            //si hay exito en la peticion
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());//la lista se llenara con los resultados
            }
            //si la peticion falla
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }

}
