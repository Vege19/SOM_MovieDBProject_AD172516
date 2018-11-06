package com.example.vege.moviedb_ad172516.models;

import com.example.vege.moviedb_ad172516.network.MovieDBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    //retrofit utilities
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MovieDBService api;
    private static  MovieRepository repository;

    public MovieRepository(MovieDBService api) {
        this.api = api;
    }

    //configuracion de la base de retrofit
    public static MovieRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MovieRepository(retrofit.create(MovieDBService.class));

        }

        return repository;
    }

    //llamamos los datos accediento con nuestra api_key
    public void getMovies(final OnGetMovieCallBack callBack) {

        api.getPopularMovies("888eed6d5b3879fea3cf535a3b85d827", "es-ES")
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                //si hay exito se obtendran los datos
                                callBack.onSuccess(moviesResponse.getMovies());

                            } else {
                                callBack.onError();

                            }
                        } else {
                            callBack.onError();

                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callBack.onError();

                    }

                });

    }

}
