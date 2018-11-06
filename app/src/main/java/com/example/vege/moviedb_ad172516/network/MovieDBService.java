package com.example.vege.moviedb_ad172516.network;

import com.example.vege.moviedb_ad172516.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String api_key,
                                          @Query("language") String language);
}
