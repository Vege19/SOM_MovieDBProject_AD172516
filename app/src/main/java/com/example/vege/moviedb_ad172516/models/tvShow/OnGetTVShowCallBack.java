package com.example.vege.moviedb_ad172516.models.tvShow;

import java.util.List;

public interface OnGetTVShowCallBack {

    void onSuccess(List<TVShow> tvShows);

    void onError();
}
