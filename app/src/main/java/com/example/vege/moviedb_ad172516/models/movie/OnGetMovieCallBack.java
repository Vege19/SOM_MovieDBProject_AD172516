package com.example.vege.moviedb_ad172516.models.movie;

import java.util.List;

public interface OnGetMovieCallBack {

    void onSuccess(List<Movie> movies);

    void onError();

}
