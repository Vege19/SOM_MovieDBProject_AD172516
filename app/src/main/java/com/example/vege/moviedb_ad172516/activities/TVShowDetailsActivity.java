package com.example.vege.moviedb_ad172516.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.adapters.TVShowsAdapter;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TVShowDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mRating;
    private ImageView mBackground;
    private TextView mGenres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);

        //R
        mOverview = findViewById(R.id.tvshowDetailOverview);
        mReleaseDate = findViewById(R.id.tvshowDetailReleaseDate);
        mRating = findViewById(R.id.popularTVShowsRating);
        mBackground = findViewById(R.id.tvshowDetailsBackground);
        mGenres = findViewById(R.id.tvshowDetailsGenres);

        //toolbar support
        mToolbar = findViewById(R.id.tvshowDetailTooolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVShowDetailsActivity.this.finish();
            }
        });

        //restore from intent
        Intent intent = getIntent();
        TVShow getTVShow = intent.getParcelableExtra("tvshow_details");

        //inflate views
        mOverview.setText(new String(getTVShow.getTvshowSynopsis()));
        mReleaseDate.setText(new String(getTVShow.getTvshowRelease()));
        mRating.setText(new String(String.valueOf(getTVShow.getTvshowRating())));
        mGenres.setText(TVShowsAdapter.getGenres(getTVShow.getTvshowGenres()));

        //background
        Picasso.get()
                .load(getTVShow.getTvshowBackdrop())
                .into(mBackground);

        getSupportActionBar().setTitle(getTVShow.getTvshowTitle());

    }
}
