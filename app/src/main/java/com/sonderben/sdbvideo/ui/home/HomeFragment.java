package com.sonderben.sdbvideo.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.adapter.AdapterFilm4MainActivity;
import com.sonderben.sdbvideo.adapter.AdapterViewPager4HomeFragment;
import com.sonderben.sdbvideo.data.model.Category;
import com.sonderben.sdbvideo.data.model.Subtitle;
import com.sonderben.sdbvideo.data.model.Video;
import com.sonderben.sdbvideo.databinding.FragmentHomeBinding;
import com.sonderben.sdbvideo.databinding.FragmentListVideosBinding;
import com.sonderben.sdbvideo.repository.GeneralRepository;
import com.sonderben.sdbvideo.ui.DetailsVideo.DetailsVideoFragment;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    OnBackPressedCallback callback;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this.requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bodyLayout = binding.body;
        playerView = binding.playerView;
        scrollView = binding.scrollview;
        play = binding.play;
        mute = binding.mute;

        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            Rect bound = new Rect();
            scrollView.getHitRect(bound);
            if (playerView.getLocalVisibleRect(bound)) {
                mSimpleExoPlayer.play();
            } else mSimpleExoPlayer.pause();
        });

        suggestionFilm();
        getCategories();

        listsSubtitle.add(new Subtitle(null,"English",
                "https://sdbvideo.s3.amazonaws.com/film/sub/Alger+pleur_en.srt",
                null,"author"));
        listsSubtitle.add(new Subtitle(null,"French",
                "https://sdbvideo.s3.amazonaws.com/film/sub/algerie.srt","","author"));

        return root;
    }

    public void suggestionFilm() {
        String urlFilm = "https://sdbvideo.s3.amazonaws.com/film/El+Tiempo+Contigo+-+Tr%C3%A1iler+Oficial+(Sub.+Espa%C3%B1ol)+-+YouTube.mkv";

        mSimpleExoPlayer =new ExoPlayer.Builder(getContext()).build();
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(urlFilm)
                //.setSubtitleConfigurations(Collections.singletonList(sub))
                .build();
        mSimpleExoPlayer.setMediaItem(mediaItem);
        playerView.setPlayer(mSimpleExoPlayer);
        mSimpleExoPlayer.setMediaItem(mediaItem);
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.play();

        play.setOnClickListener(i -> {
            Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
            intent.putExtra("URL_FILM", urlFilm);
            intent.putExtra("LIST_SUBTITLE",listsSubtitle);
            startActivity(intent);
        });
        mute.setOnClickListener(x -> {
            if (mSimpleExoPlayer.getVolume() != 0.0f) {
                mute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_volume_off_30, 0, 0, 0);
                currentVolume = mSimpleExoPlayer.getVolume();
                mSimpleExoPlayer.setVolume(0.0f);
            } else {
                mSimpleExoPlayer.setVolume(currentVolume);
                mute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_volume_up_30, 0, 0, 0);
            }
        });
    }

    public void getCategories() {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        GeneralRepository repository = retrofit.create(GeneralRepository.class);
        Call<List<Category>> categoryCall = repository.getCategories("Bearer " + preferences.getToken());
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();

                    for (int i = 0; i < categories.size(); i++) {
                        // new AlertDialog.Builder(HomeFragment.this.getContext()).setMessage(categories.get(i).getCode()+": "+categories.get(i).getName()).show();
                        getVideosByCategory(categories.get(i).getCode(), categories.get(i).getName());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    public void getVideosByCategory(int categoryCode, String categoryName) {
        Retrofit retrofit = Utils.getInstanceRetrofit();
        GeneralRepository repository = retrofit.create(GeneralRepository.class);
        Call<List<Video>> categoryCall = repository.getVideoByCategory(categoryCode, preferences.getIdProfile(), 0, "Bearer " + preferences.getToken());
        categoryCall.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {

                if (response.isSuccessful()) {
                    List<Video> videos = response.body();
                    if (videos.size() > 0) {

                        // new AlertDialog.Builder(HomeFragment.this.getContext()).setMessage(simpleFilms.get(0).getId()+" cool").show();
                        TextView titleCategory = new TextView(HomeFragment.this.getContext());
                        titleCategory.setText(categoryName);


                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(20, 0, 0, 20);

                        titleCategory.setLayoutParams(lp);
                        titleCategory.setTypeface(null, Typeface.BOLD);
                        titleCategory.setTextSize(24);
                        titleCategory.setTextColor(getResources().getColor(R.color.white));


                        textViews.add(titleCategory);
                        bodyLayout.addView(titleCategory);


                        RecyclerView recyclerView = new RecyclerView(HomeFragment.this.getContext());
                        LinearLayout.LayoutParams lpR = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lpR.setMargins(20, 0, 0, 20);
                        recyclerView.setLayoutParams(lpR);
                        recyclerView.setFocusable(false);


                        AdapterFilm4MainActivity adapter = new AdapterFilm4MainActivity(videos, HomeFragment.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                        bodyLayout.addView(recyclerView);
                    }

                } else {
                    try {
                        String a = response.errorBody().string();
                        new AlertDialog.Builder(HomeFragment.this.getContext()).setMessage(a + "no cool").show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                new AlertDialog.Builder(HomeFragment.this.getContext()).setMessage("failure: " + t.getMessage()).show();
            }
        });
    }


    PlayerView playerView;
    ExoPlayer mSimpleExoPlayer;
    TextView mute;
    float currentVolume;
    ScrollView scrollView;
    private final Preferences preferences = Preferences.getPreferenceInstance(this.getContext());
    private LinearLayout bodyLayout;
    private List<TextView> textViews = new ArrayList<>(10);
    private List<AdapterFilm4MainActivity> adapterFilm4MainActivity = new ArrayList<>(10);
    ArrayList<Subtitle>listsSubtitle=new ArrayList<>(3);
    private MaterialButton play;


    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(ListVideosFragment.this.getContext(),"onPause",Toast.LENGTH_SHORT).show();
        mSimpleExoPlayer.setPlayWhenReady(false);
        mSimpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(ListVideosFragment.this.getContext(),"onResume",Toast.LENGTH_SHORT).show();
        mSimpleExoPlayer.prepare();
        mSimpleExoPlayer.setPlayWhenReady(true);
        mSimpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(ListVideosFragment.this.getContext(),"onStop",Toast.LENGTH_SHORT).show();
        mSimpleExoPlayer.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}