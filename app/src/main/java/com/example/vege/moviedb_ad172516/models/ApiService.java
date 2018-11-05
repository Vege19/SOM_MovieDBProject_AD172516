package com.example.vege.moviedb_ad172516.models;

import retrofit.Callback;
import retrofit.http.GET;

public interface ApiService {

    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
}
