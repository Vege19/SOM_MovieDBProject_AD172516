package com.example.vege.moviedb_ad172516.models.tvShow;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TVShow implements Parcelable {

    @SerializedName("name")
    private String tvshowTitle;

    @SerializedName("overview")
    private String tvshowSynopsis;

    @SerializedName("poster_path")
    private String tvshowPoster;

    @SerializedName("backdrop_path")
    private String tvshowBackdrop;

    @SerializedName("first_air_date")
    private String tvshowRelease;

    @SerializedName("vote_average")
    private float tvshowRating;

    protected TVShow(Parcel in) {
        tvshowTitle = in.readString();
        tvshowSynopsis = in.readString();
        tvshowPoster = in.readString();
        tvshowBackdrop = in.readString();
        tvshowRelease = in.readString();
        tvshowRating = in.readFloat();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public String getTvshowTitle() {
        return tvshowTitle;
    }

    public void setTvshowTitle(String tvshowTitle) {
        this.tvshowTitle = tvshowTitle;
    }

    public String getTvshowSynopsis() {
        return tvshowSynopsis;
    }

    public void setTvshowSynopsis(String tvshowSynopsis) {
        this.tvshowSynopsis = tvshowSynopsis;
    }

    public String getTvshowPoster() {
        return tvshowPoster;
    }

    public void setTvshowPoster(String tvshowPoster) {
        this.tvshowPoster = tvshowPoster;
    }

    public String getTvshowBackdrop() {
        return "http://image.tmdb.org/t/p/w1280" + tvshowBackdrop;
    }

    public void setTvshowBackdrop(String tvshowBackdrop) {
        this.tvshowBackdrop = tvshowBackdrop;
    }

    public String getTvshowRelease() {
        return tvshowRelease;
    }

    public void setTvshowRelease(String tvshowRelease) {
        this.tvshowRelease = tvshowRelease;
    }

    public float getTvshowRating() {
        return tvshowRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tvshowTitle);
        dest.writeString(tvshowSynopsis);
        dest.writeString(tvshowPoster);
        dest.writeString(tvshowBackdrop);
        dest.writeString(tvshowRelease);
        dest.writeFloat(tvshowRating);
    }
}
