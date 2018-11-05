package com.example.vege.moviedb_ad172516.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("overview")
    private String movieSynopsis;

    @SerializedName("poster_path")
    private String moviePoster;

    @SerializedName("backdrop_path")
    private String movieBackdrop;

    @SerializedName("release_date")
    private String movieRelease;

    public Movie() {

    }

    protected Movie(Parcel in) {
        movieTitle = in.readString();
        movieSynopsis = in.readString();
        moviePoster = in.readString();
        movieBackdrop = in.readString();
        movieRelease = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public String getMovieBackdrop() {
        return "http://image.tmdb.org/t/p/w1280" + movieBackdrop;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(movieSynopsis);
        dest.writeString(moviePoster);
        dest.writeString(movieBackdrop);
        dest.writeString(movieRelease);
    }

    //api class
    public class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }


}
