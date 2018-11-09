package com.example.vege.moviedb_ad172516.models.genre;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    private int genreId;

    @SerializedName("name")
    private String genreName;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

}
