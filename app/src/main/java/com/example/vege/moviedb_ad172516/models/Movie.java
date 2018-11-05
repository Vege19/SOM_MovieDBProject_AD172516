package com.example.vege.moviedb_ad172516.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("overview")
    private String movieSynopsis;

    @SerializedName("backdrop_path")
    private String moviePoster;

    public Movie() {

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public void setMovieSynopsis(String movieSynopsis) {
        this.movieSynopsis = movieSynopsis;
    }

    public String getMoviePoster() {
        return "http://image.tmdb.org/t/p/w500" + moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    //api class
    public class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
