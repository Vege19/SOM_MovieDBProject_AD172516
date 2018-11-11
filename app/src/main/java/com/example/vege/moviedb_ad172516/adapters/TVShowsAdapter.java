package com.example.vege.moviedb_ad172516.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vege.moviedb_ad172516.activities.MovieDetailsActivity;
import com.example.vege.moviedb_ad172516.R;
import com.example.vege.moviedb_ad172516.activities.TVShowDetailsActivity;
import com.example.vege.moviedb_ad172516.models.genre.Genre;
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder> {

    private List<TVShow> tvShowList;
    private static List<Genre> allGenres;
    private Context context;
    private String imageURL = "http://image.tmdb.org/t/p/w500";

    public TVShowsAdapter(List<TVShow> tvShowList, List<Genre> allGenres, Context context) {
        this.tvShowList = tvShowList;
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

        viewHolder.mTitle.setText(tvShow.getTvshowTitle());
        viewHolder.mGenres.setText(getGenres(tvShow.getTvshowGenres()));

        //picasso para obtener las imagenes
        Picasso.get()
                .load(imageURL + tvShow.getTvshowBackdrop())
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

        private TextView mTitle;
        private TextView mGenres;
        private ImageView mBackdrop;
        private RelativeLayout mItem;

        public TVShowsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tvTVShowTitle);
            mBackdrop = itemView.findViewById(R.id.ivTVShowPoster);
            mItem = itemView.findViewById(R.id.rlTVshowItem);
            mGenres = itemView.findViewById(R.id.tvTVShowGenres);
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
}
