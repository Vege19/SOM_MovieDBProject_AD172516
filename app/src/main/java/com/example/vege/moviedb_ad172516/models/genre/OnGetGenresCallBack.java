package com.example.vege.moviedb_ad172516.models.genre;

import java.util.List;

public interface OnGetGenresCallBack {

    void OnSuccess(List<Genre> genres);

    void onError();
}
