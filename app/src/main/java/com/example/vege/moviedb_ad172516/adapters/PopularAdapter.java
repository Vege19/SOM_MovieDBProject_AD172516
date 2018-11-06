package com.example.vege.moviedb_ad172516.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.MovieDetailsActivity;
import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<Movie> popularMovieList;
    private Context context;

    public PopularAdapter(List<Movie> movies, Context context) {
        this.popularMovieList = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        ViewHolder pVH = new ViewHolder(view);
        return pVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Movie movie = popularMovieList.get(i);

        viewHolder.mTitle.setText(movie.getMovieTitle());

        //picasso para obtener las imagenes
        Picasso.get()
                .load(movie.getMovieBackdrop())
                .error(R.drawable.ic_signal_wifi_off_white_24dp)
                .into(viewHolder.mPoster);

        //intent con parcelable
        viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("details", movie);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mPoster;
        RelativeLayout mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tvTitle);
            mPoster = itemView.findViewById(R.id.ivPoster);
            mItem = itemView.findViewById(R.id.rlItem);

        }

    }

}
