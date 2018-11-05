package com.example.vege.moviedb_ad172516.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<Movie> popularMoviesList;
    private Context context;

    public PopularAdapter(Context context) {
        this.popularMoviesList = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = popularMoviesList.get(i);

        viewHolder.mTitle.setText(movie.getMovieTitle());

        //picasso para obtener las imagenes
        Picasso.with(context)
                .load(movie.getMoviePoster())
                .placeholder(R.drawable.ic_broken_image_white_24dp)
                .into(viewHolder.mPoster);

    }

    @Override
    public int getItemCount() {
        return (popularMoviesList == null) ? 0 : popularMoviesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tvTitle);
            mPoster = itemView.findViewById(R.id.ivPoster);

        }

    }

    public void setMovieList(List<Movie> movieList) {
        this.popularMoviesList.clear();
        this.popularMoviesList.addAll(movieList);
        notifyDataSetChanged();

    }
}
