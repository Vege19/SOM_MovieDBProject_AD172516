package com.example.vege.moviedb_ad172516.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //toolbar config
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.detailTooolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.this.finish();
            }
        });

        //recuperamos los datos
        Intent intent = getIntent();
        Movie getMovie = intent.getParcelableExtra("details");

        String poster = getMovie.getMoviePoster();
        String backdrop = getMovie.getMovieBackdrop();
        String title = getMovie.getMovieTitle();
        String overview = getMovie.getMovieSynopsis();
        String releaseDate = getMovie.getMovieRelease();

        //llamamos resources y seteamos los datos recuperados
        final ImageView posterBackground = findViewById(R.id.ivDetailsBackground);

        Picasso.get()
                .load(backdrop)
                .placeholder(R.drawable.ic_broken_image_white_24dp)
                .error(R.drawable.ic_signal_wifi_off_white_24dp)
                .into(posterBackground);

        TextView detailsOverview =findViewById(R.id.tvOverview);
        detailsOverview.setText(overview + overview + overview);

        TextView detailsRelease = findViewById(R.id.tvReleaseDate);
        detailsRelease.setText("Release date: " + releaseDate);

        getSupportActionBar().setTitle(title);

    }

}
