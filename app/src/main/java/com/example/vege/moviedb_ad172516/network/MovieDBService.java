package com.example.vege.moviedb_ad172516.network;

import com.example.vege.moviedb_ad172516.models.genre.GenresResponse;
import com.example.vege.moviedb_ad172516.models.movie.MoviesResponse;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDBService {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String api_key,
                                          @Query("language") String language);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String api_key,
                                          @Query("language") String language);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpComingMovies(@Query("api_key") String api_key,
                                           @Query("language") String language);

    @GET("tv/popular")
    Call<TVShowsResponse> getPopularTVShows(@Query("api_key") String api_key,
                                            @Query("language") String language);

    @GET("tv/top_rated")
    Call<TVShowsResponse> getTopRatedTVShows(@Query("api_key") String api_key,
                                            @Query("language") String language);

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String api_key,
            @Query("language") String language);

}
