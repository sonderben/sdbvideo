package com.sonderben.sdbvideo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Profile;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterImagesEditProfileActivity extends RecyclerView.Adapter<AdapterImagesEditProfileActivity.ViewHolder> {

    List<Profile.Image> images;
    AlertDialog dialog;
    Activity activity;
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    String urlImg=null;

    public AdapterImagesEditProfileActivity(Activity activity, List<Profile.Image> images, AlertDialog dialog) {
        this.activity = activity;
        this.dialog = dialog;
        this.images = images;
    }
    public String getUrlImg(){
        return urlImg;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custum_list_images_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterImagesEditProfileActivity.ViewHolder holder, int position) {


        Picasso.get()
                .load(images.get(position).getUrl())
                .fit()
                .centerCrop()
                .into(holder.image);


        holder.itemView.setOnClickListener(x -> {


            urlImg=images.get(position).getUrl();


                    dialog.cancel();

        });


        if (position == images.size() - 1) {
            lp.setMargins(0, 0, 20, 0);
            holder.itemView.setLayoutParams(lp);
        }


    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);


        }
    }
}
