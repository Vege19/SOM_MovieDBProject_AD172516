package com.example.vege.moviedb_ad172516.models;

import com.example.vege.moviedb_ad172516.models.Movie;

import java.util.List;

public interface OnGetMovieCallBack {

    void onSuccess(List<Movie> movies);

    void onError();

}
