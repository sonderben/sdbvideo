package com.sonderben.sdbvideo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.MainActivity2;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.ui.choose_profile.EnterPinActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterProfile4ChooseProfileActivity extends RecyclerView.Adapter<AdapterProfile4ChooseProfileActivity.ViewHolder> {

    List<Profile> profiles;
    Activity activity;

    public AdapterProfile4ChooseProfileActivity(List<Profile> episodes, Activity activity) {
        this.activity = activity;
        this.profiles = episodes;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_view4recyclerview_profile_choose_profile_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterProfile4ChooseProfileActivity.ViewHolder holder, int position) {


        holder.profileName.setText(profiles.get(position).getName());
        Picasso.get()
                .load(profiles.get(position).getUrlImg())
                .fit()
                .centerCrop()
                .into(holder.profilePhoto);
        holder.itemView.setOnClickListener(x -> {

            Intent intent = new Intent(activity, EnterPinActivity.class);
            intent.putExtra("name",profiles.get(position).getName());
            intent.putExtra("url",profiles.get(position).getUrlImg());
            intent.putExtra("pin",profiles.get(position).getPin());
            intent.putExtra("id",profiles.get(position).getId());
            intent.putExtra("isMain",profiles.get(position).getMainProfile());

            //Pair


            Pair[]pairs=new Pair[2];
            pairs[0]=new Pair<View,String>(holder.profilePhoto,"imgprofile");
            pairs[1]=new Pair<View,String>(holder.profileName,"profilename");


            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(activity,pairs);






            activity.startActivity(intent,options.toBundle());
            //activity.finish();
        });


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhoto;
        //ProgressBar progressBar;
        TextView profileName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.profile_foto);
            profileName = itemView.findViewById(R.id.profile_nam);

        }
    }
}
