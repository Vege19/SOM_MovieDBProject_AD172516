package com.example.vege.moviedb_ad172516.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.activities.MovieDetailsActivity;
import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements Filterable {

    private List<Movie> popularMovieList;
    private static List<Genre> allGenres;
    private List<Movie> searchList;
    private Context context;
    private String imageURL = "http://image.tmdb.org/t/p/w500";

    public MoviesAdapter(List<Movie> movies, List<Genre> allGenres, Context context) {
        this.popularMovieList = movies;
        this.searchList = new ArrayList<>(popularMovieList);
        this.allGenres = allGenres;
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
        viewHolder.mGenre.setText(getGenres(movie.getMovieGenres()));

        //picasso para obtener las imagenes
        Picasso.get()
                .load(imageURL + movie.getMovieBackdrop())
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
        private TextView mGenre;
        private ImageView mPoster;
        RelativeLayout mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tvTitle);
            mPoster = itemView.findViewById(R.id.ivPoster);
            mItem = itemView.findViewById(R.id.rlItem);
            mGenre = itemView.findViewById(R.id.tvGenres);

        }

    }

    //getGenres
    public static String getGenres(List<Integer> genreIds) {
        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            for (Genre genre : allGenres) {
                if (genre.getGenreId() == genreId) {
                    movieGenres.add(genre.getGenreName());
                    break;
                }
            }
        }

        return TextUtils.join("  ·  ", movieGenres);
    }


    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchList);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Movie movie : searchList) {
                    if (movie.getMovieTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //se limpiara la lista actual y mostrara los items filtrados
            popularMovieList.clear();
            popularMovieList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
