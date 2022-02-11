package com.sonderben.sdbvideo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Video;
import com.sonderben.sdbvideo.ui.DetailsVideo.DetailsVideoFragment;
import com.sonderben.sdbvideo.ui.home.HomeFragment;
import com.sonderben.sdbvideo.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterFilm4MainActivity extends RecyclerView.Adapter<AdapterFilm4MainActivity.ViewHolder> {

    List<Video> films;
    HomeFragment homeFragment;
    HomeViewModel homeViewModel;
    public AdapterFilm4MainActivity(List<Video> films, HomeFragment homeFragment){
        this.films=films;
        this.homeFragment=homeFragment;
        homeViewModel =
                new ViewModelProvider(homeFragment.requireActivity()).get(HomeViewModel.class);
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
                .load(films.get(position).getPoster())
                .fit()
                .centerCrop()
                .into(holder.profilePhoto);

        Log.i("mytaggurl",films.get(position).getPoster()+" pos");
        /*
        Fragment nuevoFragmento = new BlankFragment();
 FragmentTransaction transaction = getFragmentManager().beginTransaction();
 transaction.replace(R.id.fragment_container, nuevoFragmento);
 transaction.addToBackStack(null);

 // Commit a la transacción
 transaction.commit();
         */


        holder.itemView.setOnClickListener(x->{
            Video video= films.get(position);
            homeViewModel.setCurrentFragment(DetailsVideoFragment.class.toString());
            video.setPoster("wwwwww.poster.url");
            DetailsVideoFragment bottomSheet= DetailsVideoFragment.newInstance(video);
            bottomSheet.show(homeFragment.getParentFragmentManager(), "DetailVideoBottomSheet");
        });



    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhoto;
        //ProgressBar progressBar;
        TextView profileName;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            profilePhoto=itemView.findViewById(R.id.film_foto);
            //profileName=itemView.findViewById(R.id.profile_nam);
            //homeViewModel.setCurrentFragment(ListVideosFragment.class.toString());

        }
    }
}
