package com.example.vege.moviedb_ad172516.models.movie;

import com.example.vege.moviedb_ad172516.BuildConfig;
import com.example.vege.moviedb_ad172516.models.genre.GenresResponse;
import com.example.vege.moviedb_ad172516.models.genre.OnGetGenresCallBack;
import com.example.vege.moviedb_ad172516.network.MovieDBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopRatedMoviesRepository {

    //retrofit utilities
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MovieDBService api;
    private static TopRatedMoviesRepository repository;

    public TopRatedMoviesRepository(MovieDBService api) {
        this.api = api;
    }

    //configuracion de la base de retrofit
    public static TopRatedMoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new TopRatedMoviesRepository(retrofit.create(MovieDBService.class));

        }

        return repository;
    }

    //llamamos los datos accediento con nuestra api_key
    public void getMovies(final OnGetMovieCallBack callBack) {

        api.getTopRatedMovies(BuildConfig.MOVIEDBAPIKEY, "es-ES")
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

    public void getTopRatedMoviesGenres(final OnGetGenresCallBack genresCallBack) {
        api.getGenres(BuildConfig.MOVIEDBAPIKEY, "es-ES")
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                        if (response.isSuccessful()) {
                            GenresResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                genresCallBack.OnSuccess(genresResponse.getGenres());
                            } else {
                                genresCallBack.onError();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GenresResponse> call, Throwable t) {
                        genresCallBack.onError();
                    }
                });
    }
}
