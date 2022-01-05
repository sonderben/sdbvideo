package com.sonderben.sdbvideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.entity.Episode;
import com.sonderben.sdbvideo.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEpo4PlayerView extends RecyclerView.Adapter<AdapterEpo4PlayerView.ViewHolder> {

    List<Episode>episodes;
    public  AdapterEpo4PlayerView(List<Episode>episodes){
        this.episodes=episodes;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_view_epo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterEpo4PlayerView.ViewHolder holder, int position) {

        holder.duration.setText( Utils.timeMillisToTextTime( episodes.get(position).getDuration() ) );
        holder.synopsis.setText(episodes.get(position).getSynopsis());
        holder.titleFilm.setText(episodes.get(position).getTitle());
        Picasso.get()
                .load(episodes.get(position).getUrl_poster())
                .fit()
                .centerCrop()
                .into(holder.posterFilm);

        holder.progressBar.setProgress(23);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterFilm,download;
        ProgressBar progressBar;
        TextView titleFilm,synopsis,duration;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            posterFilm=itemView.findViewById(R.id.poster_film);
            download=itemView.findViewById(R.id.download);
            progressBar=itemView.findViewById(R.id.progress_bar);
            titleFilm=itemView.findViewById(R.id.title_film);
            synopsis=itemView.findViewById(R.id.synopsis);
            duration=itemView.findViewById(R.id.duration);
        }
    }
}
