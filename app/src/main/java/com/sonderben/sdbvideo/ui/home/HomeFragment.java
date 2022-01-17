package com.sonderben.sdbvideo.ui.home;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.adapter.AdapterFilm4MainActivity;
import com.sonderben.sdbvideo.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private LinearLayout bodyLayout;
    private RecyclerView []recyclerViews=new RecyclerView[6];
    private TextView[]textViews=new TextView[6];
    private MaterialButton play;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);



        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bodyLayout= binding.body;
        playerView= binding.playerView;
        scrollView=binding.scrollview;
        play= binding.play;
        mute=binding.mute;

        mSimpleExoPlayer = new SimpleExoPlayer.Builder(requireContext()).build();
        String url="https://ejemploht.s3.us-east-2.amazonaws.com/officielle+(VF)+-+YouTube.mkv";
        MediaItem mediaItem=new MediaItem.Builder()
                .setUri(url)
                .build();
        mSimpleExoPlayer.setMediaItem(mediaItem);
        playerView.setPlayer(mSimpleExoPlayer);

        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.setPlayWhenReady(true);

        play.setOnClickListener(i->{
            Intent intent=new Intent(getContext(),VideoPlayerActivity.class);
            intent.putExtra("URL_FILM",url);
            startActivity(intent);
        });

scrollView.fullScroll(ScrollView.FOCUS_UP);
        mute.setOnClickListener(x->{
            if(mSimpleExoPlayer.getVolume()!=0.0f){
                mute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_volume_off_30, 0, 0, 0);
                currentVolume= mSimpleExoPlayer.getVolume();
                mSimpleExoPlayer.setVolume(0.0f);
            }else{
                mSimpleExoPlayer.setVolume(currentVolume);
                mute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_volume_up_30, 0, 0, 0);
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Rect bound=new Rect();
                scrollView.getHitRect(bound);
                if(playerView.getLocalVisibleRect(bound)){
                    mSimpleExoPlayer.play();
                }
                else mSimpleExoPlayer.pause();
            }
        });




        List<String>title=new ArrayList<>(20);
        AdapterFilm4MainActivity adapterFilm4MainActivity[]=new AdapterFilm4MainActivity[recyclerViews.length];
        for (int i = 0; i < recyclerViews.length; i++) {
            title.add("title "+i);
            adapterFilm4MainActivity[i]=new AdapterFilm4MainActivity(title);
        }



        //final TextView textView = binding.logo;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        for (int i = 0; i < recyclerViews.length; i++) {
            recyclerViews[i]=new RecyclerView(getContext());
            textViews[i]=new TextView(getContext());


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(20, 0, 0, 20);

            textViews[i].setLayoutParams(lp);
            textViews[i].setTypeface(null, Typeface.BOLD);
            textViews[i].setTextSize(24);
            textViews[i].setText("title "+i);
            textViews[i].setTextColor(getResources().getColor(R.color.white));
            bodyLayout.addView(textViews[i]);

            LinearLayout.LayoutParams lpR = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lpR.setMargins(20, 0, 0, 20);
            recyclerViews[i].setLayoutParams(lpR);
            recyclerViews[i].setFocusable(false);
            recyclerViews[i].setAdapter(adapterFilm4MainActivity[i]);
            recyclerViews[i].setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
            bodyLayout.addView(recyclerViews[i]);
        }



        return root;
    }

    PlayerView playerView;
    SimpleExoPlayer mSimpleExoPlayer;
    TextView mute;
    float currentVolume;
    ScrollView scrollView;


    @Override
    public void onPause() {
        super.onPause();
        mSimpleExoPlayer.setPlayWhenReady(false);
        mSimpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.setPlayWhenReady(true);
        mSimpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSimpleExoPlayer.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}