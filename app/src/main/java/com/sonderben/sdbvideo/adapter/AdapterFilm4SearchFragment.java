package com.sonderben.sdbvideo.adapter;

import android.app.Fragment;
import androidx.fragment.app.FragmentManager;
//androidx.fragment.app.FragmentManager c
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.SimpleFilm;
import com.sonderben.sdbvideo.data.model.Video;
import com.sonderben.sdbvideo.ui.DetailsVideo.DetailsVideoFragment;
import com.sonderben.sdbvideo.ui.dashboard.DashboardFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class AdapterFilm4SearchFragment extends RecyclerView.Adapter<AdapterFilm4SearchFragment.ViewHolder> {

    List<Video> films;
    DateFormat dateFormat = DateFormat.getDateInstance();
    FragmentManager manager;

    //Fragment fragment;
    public AdapterFilm4SearchFragment(List<Video> films,FragmentManager manager) {
        this.films = films;
        this.manager=manager;
        // this.profiles =episodes;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_item4recycleview_search_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterFilm4SearchFragment.ViewHolder holder, int position) {


        Picasso.get()
                .load(films.get(position).getPoster())
                .fit()
                .centerCrop()
                .into(holder.img);

        holder.itemView.setOnClickListener(x->{
            DetailsVideoFragment fragment= DetailsVideoFragment.newInstance(films.get(position));
           // androidx.fragment.app.FragmentManager c=fragment.getParentFragmentManager();
            fragment.show(manager,"DetailVideoBottomSheetDetails");
        });

        //Date date=new Date(films.get(position).getReleaseDate());
        //Calendar calendar=new GregorianCalendar(TimeZone.getTimeZone(films.get(position).getReleaseDate()));

        //holder.duration.setText(films.get(position).getDuration()+"");
        //holder.title.setText(films.get(position).getTitle());
        //  holder.releaseDate.setText(dateFormat.format(date));


    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        //ProgressBar progressBar;
        TextView title, releaseDate, duration;
        View view;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.release_date);
            duration = itemView.findViewById(R.id.duration);


        }
    }
}
