package com.sonderben.sdbvideo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.MainActivity2;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.utils.Preferences;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterFilm4MainActivity extends RecyclerView.Adapter<AdapterFilm4MainActivity.ViewHolder> {

    List<String> title;
    public AdapterFilm4MainActivity(List<String> title){
        this.title=title;
       // this.profiles =episodes;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_view4recyclerview_film,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterFilm4MainActivity.ViewHolder holder, int position) {



        Picasso.get()
                .load("https://store-images.s-microsoft.com/image/apps." +
                        "46013.69399725068812250.e18e30fe-4fd2-40d8-9c22-033732f7b7d3." +
                        "9a8725dd-9bf8-4c65-99b1-47b06293cc7b")
                .fit()
                .centerCrop()
                .into(holder.profilePhoto);



    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhoto;
        //ProgressBar progressBar;
        TextView profileName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profilePhoto=itemView.findViewById(R.id.film_foto);
            //profileName=itemView.findViewById(R.id.profile_nam);

        }
    }
}
