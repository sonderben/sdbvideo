package com.sonderben.sdbvideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.utils.Utils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterProfile4MeFragmen extends RecyclerView.Adapter<AdapterProfile4MeFragmen.ViewHolder> {

    List<Profile> profiles;
    public AdapterProfile4MeFragmen(List<Profile>episodes){
        this.profiles =episodes;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_view4recyclerview_profile_me_fragment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterProfile4MeFragmen.ViewHolder holder, int position) {


        holder.profileName.setText(profiles.get(position).getName());
        Picasso.get()
                .load(profiles.get(position).getUrlImg())
                .fit()
                .centerCrop()
                .into(holder.profilePhoto);


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhoto;
      //  ProgressBar progressBar;
        TextView profileName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profilePhoto=itemView.findViewById(R.id.profile_foto);
            profileName=itemView.findViewById(R.id.profile_nam);

        }
    }
}
