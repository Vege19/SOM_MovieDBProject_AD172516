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
import com.example.vege.moviedb_ad172516.models.tvShow.TVShow;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder> {

    private List<TVShow> tvShowList;
    private Context context;

    public TVShowsAdapter(List<TVShow> tvShowList, Context context) {
        this.tvShowList = tvShowList;
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

        //picasso para obtener las imagenes
        Picasso.get()
                .load(tvShow.getTvshowBackdrop())
                .error(R.drawable.ic_signal_wifi_off_white_24dp)
                .into(viewHolder.mBackdrop);

        //intent con parcelable
        viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
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
        private ImageView mBackdrop;
        private RelativeLayout mItem;

        public TVShowsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tvTVShowTitle);
            mBackdrop = itemView.findViewById(R.id.ivTVShowPoster);
            mItem = itemView.findViewById(R.id.rlTVshowItem);
        }
    }
}
