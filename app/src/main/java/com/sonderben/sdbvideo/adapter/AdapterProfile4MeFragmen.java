package com.sonderben.sdbvideo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.MainActivity2;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Profile;
import com.sonderben.sdbvideo.ui.choose_profile.EditProfileActivity;
import com.sonderben.sdbvideo.ui.choose_profile.EnterPinActivity;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterProfile4MeFragmen extends RecyclerView.Adapter<AdapterProfile4MeFragmen.ViewHolder> {

    List<Profile> profiles;
    Activity activity;
    Preferences preferences;
    public AdapterProfile4MeFragmen(List<Profile>episodes,Activity activity){
        this.activity=activity;
        this.profiles =episodes;
        preferences=Preferences.getPreferenceInstance(activity);
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


        if(position< profiles.size()) {


            holder.profileName.setText(profiles.get(position).getName());
            Picasso.get()
                    .load(profiles.get(position).getUrlImg())
                    .fit()
                    .centerCrop()
                    .into(holder.profilePhoto);

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if( !preferences.getIsMainProfilesPreferences() && preferences.getIdProfile().longValue() != profiles.get(position).getId().longValue() ) {

                        Toast.makeText(activity,"You don't have the permission to change this profile.",Toast.LENGTH_SHORT).show();


                    }
                    else {
                        Intent intent = new Intent(activity, EditProfileActivity.class);
                    intent.putExtra("id", profiles.get(position).getId());
                    intent.putExtra("url", profiles.get(position).getUrlImg());
                    intent.putExtra("name", profiles.get(position).getName());
                    intent.putExtra("pin", profiles.get(position).getPin());
                    intent.putExtra("main_profile", profiles.get(position).getMainProfile());
                    intent.putExtra("age", profiles.get(position).getAgeCategory());
                    intent.putExtra("lang", profiles.get(position).getDefaultLanguage());
                    intent.putExtra("MODE","EDIT");
                    activity.startActivity(intent);
                    }
                    return true;
                }
            });
            holder.itemView.setOnClickListener(x->{
                if( !profiles.get(position).getId().equals(preferences.getIdProfile()) ){
                    Intent intent=new Intent(activity, EnterPinActivity.class);


                    intent.putExtra("name",profiles.get(position).getName());
                    intent.putExtra("url",profiles.get(position).getUrlImg());
                    intent.putExtra("pin",profiles.get(position).getPin());
                    intent.putExtra("id",profiles.get(position).getId());
                    intent.putExtra("isMain",profiles.get(position).getMainProfile());



                    Pair[]pairs=new Pair[2];
                    pairs[0]=new Pair<View,String>(holder.profilePhoto,"imgprofile");
                    pairs[1]=new Pair<View,String>(holder.profileName,"profilename");


                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(activity,pairs);



                    activity.startActivity(intent,options.toBundle());

                    return;
                }
                Toast.makeText(activity,"You are already connected to this profile",Toast.LENGTH_SHORT).show();

            });


        }

        else{
            holder.profileName.setText("New Profile");
            holder.profilePhoto.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_add_30));
            holder.itemView.setOnClickListener(x->{
                Intent intent = new Intent(activity, EditProfileActivity.class);
                intent.putExtra("MODE","CREATE");
                activity.startActivity(intent);
            });
            //holder.profilePhoto.setBackgroundColor();

        }


    }

    @Override
    public int getItemCount() {
        return profiles.size()+1;
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
