package com.example.vege.moviedb_ad172516.models.tvShow;

import com.example.vege.moviedb_ad172516.BuildConfig;
import com.example.vege.moviedb_ad172516.models.genre.GenresResponse;
import com.example.vege.moviedb_ad172516.models.genre.OnGetGenresCallBack;
import com.example.vege.moviedb_ad172516.network.MovieDBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularTVShowsRepository {

    //retrofit utilities
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static MovieDBService api;
    private static PopularTVShowsRepository popularTVShowsRepository;

    public PopularTVShowsRepository(MovieDBService api) {
        this.api = api;
    }

    //configuracion de la base de retrofit
    public static PopularTVShowsRepository getInstance() {
        if (popularTVShowsRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            popularTVShowsRepository = new PopularTVShowsRepository(retrofit.create(MovieDBService.class));

        }

        return popularTVShowsRepository;
    }

    //llamamos los datos accediento con nuestra api_key
    public void getPopularTVShows(final OnGetTVShowCallBack callBack) {

        api.getPopularTVShows(BuildConfig.MOVIEDBAPIKEY, "es-ES")
                .enqueue(new Callback<TVShowsResponse>() {
                    @Override
                    public void onResponse(Call<TVShowsResponse> call, Response<TVShowsResponse> response) {

                        if (response.isSuccessful()) {
                            TVShowsResponse tvShowsResponse = response.body();
                            if (tvShowsResponse != null && tvShowsResponse.getTvShows() != null) {
                                //si hay exito se obtendran los datos
                                callBack.onSuccess(tvShowsResponse.getTvShows());

                            } else {
                                callBack.onError();

                            }
                        } else {
                            callBack.onError();

                        }
                    }

                    @Override
                    public void onFailure(Call<TVShowsResponse> call, Throwable t) {
                        callBack.onError();

                    }

                });

    }


    public void getPopularTVShowsGenres(final OnGetGenresCallBack genresCallBack) {
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
