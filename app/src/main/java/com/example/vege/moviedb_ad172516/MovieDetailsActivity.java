package com.example.vege.moviedb_ad172516;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.vege.moviedb_ad172516.models.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

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

        Picasso.with(this)
                .load(backdrop)
                .placeholder(R.drawable.ic_broken_image_white_24dp)
                .into(posterBackground);

        TextView detailsTitle = findViewById(R.id.tvDetailsMovieTitle);
        detailsTitle.setText(title);

        TextView detailsOverview =findViewById(R.id.tvOverview);
        detailsOverview.setText(overview);

        TextView detailsRelease = findViewById(R.id.tvReleaseDate);
        detailsRelease.setText("Release date: " + releaseDate);

        //Back to main activity
        ImageView buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.this.finish();
            }
        });


    }

}
