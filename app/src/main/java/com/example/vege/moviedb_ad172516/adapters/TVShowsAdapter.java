package com.example.vege.moviedb_ad172516.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.example.vege.moviedb_ad172516.activities.TVShowDetailsActivity;
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.movie.Movie;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder> implements Filterable {

    private List<TVShow> tvShowList;
    private static List<Genre> allGenres;
    private List<TVShow> searchTVShows;
    private Context context;
    private String imageURL = "http://image.tmdb.org/t/p/w500";

    public TVShowsAdapter(List<TVShow> tvShowList, List<Genre> allGenres, Context context) {
        this.tvShowList = tvShowList;
        this.searchTVShows = new ArrayList<>(tvShowList);
        this.allGenres = allGenres;
        this.context = context;
    }

    @NonNull
    @Override
    public TVShowsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow, viewGroup, false);
        TVShowsViewHolder tvsVh = new TVShowsViewHolder(view);
        return tvsVh;
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsViewHolder viewHolder, int i) {
        final TVShow tvShow = tvShowList.get(i);

        //picasso para obtener las imagenes
        Picasso.get()
                .load(imageURL + tvShow.getTvshowPoster())
                .error(R.drawable.ic_signal_wifi_off_white_24dp)
                .into(viewHolder.mBackdrop);

        //intent con parcelable
        viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TVShowDetailsActivity.class);
                intent.putExtra("tvshow_details", tvShow);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public class TVShowsViewHolder extends RecyclerView.ViewHolder {

        private ImageView mBackdrop;
        private CardView mItem;

        public TVShowsViewHolder(@NonNull View itemView) {
            super(itemView);

            mBackdrop = itemView.findViewById(R.id.ivTVShowPoster);
            mItem = itemView.findViewById(R.id.rlTVshowItem);

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
        return tvShowFilter;
    }

    private Filter tvShowFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TVShow> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchTVShows);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TVShow tvShow : searchTVShows) {
                    if (tvShow.getTvshowTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(tvShow);
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
            tvShowList.clear();
            tvShowList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
