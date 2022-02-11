package com.sonderben.sdbvideo.ui.DetailsVideo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sonderben.sdbvideo.VideoPlayerActivity;
import com.sonderben.sdbvideo.adapter.AdapterFilm4SearchFragment;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.data.model.Movie;
import com.sonderben.sdbvideo.data.model.Video;
import com.sonderben.sdbvideo.repository.GeneralRepository;
import com.sonderben.sdbvideo.utils.Preferences;
import com.sonderben.sdbvideo.utils.Utils;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DetailsVideoFragment extends BottomSheetDialogFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View root;

    // TODO: Rename and change types of parameters

    private Video video;

    public DetailsVideoFragment() {
        // Required empty public constructor
    }


    public static DetailsVideoFragment newInstance(Video video) {
        DetailsVideoFragment fragment = new DetailsVideoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,video);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            video = (Video) getArguments().getSerializable(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root =inflater.inflate(R.layout.fragment_details_video,container,false);
        recyclerView= root.findViewById(R.id.recyclerview);
        radioGroup= root.findViewById(R.id.radio_group);
        RadioButton rbEpisode= root.findViewById(R.id.rb_episodes);
        RadioButton rbSimilarTitle= root.findViewById(R.id.rb_similar_title);
        title=root.findViewById(R.id.title);
        synopsis=root.findViewById(R.id.synopsis);
        category=root.findViewById(R.id.category);
        play=root.findViewById(R.id.play);

        play.setOnClickListener(x->{
            Intent intent=new Intent(getContext(), VideoPlayerActivity.class);
            //String urlFilm=getIntent().getStringExtra("URL_FILM");
            intent.putExtra("URL_FILM",urlFilm);
            startActivity(intent);
        });

        if(video.isMovie()){
            rbSimilarTitle.setChecked(true);
            rbEpisode.setVisibility(View.GONE);
        }
        StringBuilder categoryStringBuilder=new StringBuilder();

        categoryStringBuilder.append("&#8226;");
        categoryStringBuilder.append(" +"+video.getAgeCategory()+"   ");


        categoryStringBuilder.append("&#8226;");
        categoryStringBuilder.append( Utils.timeMillisToTextTime(Long.valueOf(video.getDuration())) );


        categoryStringBuilder.append("    &#8226;");

        //categoryStringBuilder.append(video.getAgeCategory());
        for (int i = 0; i < video.getCategories().size(); i++) {
            categoryStringBuilder.append(video.getCategories().get(i));
            categoryStringBuilder.append(" ");
        }




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            category.setText(Html.fromHtml(categoryStringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT));
        }else {
            category.setText(Html.fromHtml(categoryStringBuilder.toString()));
        }


        getMovieByIdVide(video.getId());
        getVideosByDescription(video.getDescription());
        return root;
    }
    TextView title,synopsis,category;
    RecyclerView recyclerView;
    AdapterFilm4SearchFragment adapter;
    RadioGroup radioGroup;
    Button play;
    String urlFilm;
    Preferences preferences=Preferences.getPreferenceInstance(DetailsVideoFragment.this.getContext());
    public void getVideosByDescription(String description){
        Retrofit retrofit= Utils.getInstanceRetrofit();
        GeneralRepository repository=retrofit.create(GeneralRepository.class);
        Call<List<Video>> categoryCall=repository.getVideoByDescription(description,preferences.getIdProfile(),0,"Bearer "+preferences.getToken());
        categoryCall.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {

                if( response.isSuccessful()){
                    List<Video>videos=response.body();
                   if(videos.size()>0){
                       adapter=new AdapterFilm4SearchFragment(videos,DetailsVideoFragment.this.getParentFragmentManager());
                       recyclerView.setLayoutManager(new LinearLayoutManager(DetailsVideoFragment.this.getContext(),RecyclerView.VERTICAL,false));
                       recyclerView.setAdapter(adapter);

                   }
                   else {
                       radioGroup.setVisibility(View.GONE);
                   }
                    //progressBar.setVisibility(View.GONE);



                }
                else {
                    try {
                        String a=response.errorBody().string();
                        new AlertDialog.Builder(DetailsVideoFragment.this.getContext()).setMessage(a+"no cool").show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                new AlertDialog.Builder(DetailsVideoFragment.this.getContext()).setMessage("failure: "+t.getMessage()).show();
            }
        });
    }

    public void getMovieByIdVide(Long idVideo){
        Retrofit retrofit= Utils.getInstanceRetrofit();
        GeneralRepository repository=retrofit.create(GeneralRepository.class);
        Call<Movie> categoryCall=repository.getMovieByVideoId(idVideo,preferences.getIdProfile(),"Bearer "+preferences.getToken());
        categoryCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if( response.isSuccessful()){
                    Movie movie=response.body();
                    title.setText(movie.getTitleSynopses().get(0).getTitle());
                    synopsis.setText(movie.getTitleSynopses().get(0).getSynopsis());
                    urlFilm=movie.getUrl();
                    play.setEnabled(true);



                }
                else {
                    try {
                        String a=response.errorBody().string();
                        new AlertDialog.Builder(DetailsVideoFragment.this.getContext()).setMessage(a+"no cool").show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                new AlertDialog.Builder(DetailsVideoFragment.this.getContext()).setMessage("failure: "+t.getMessage()).show();
            }
        });
    }

    @Override
    public void onResume() {
        //Toast.makeText(DetailsVideoFragment.this.getContext(),"on resume",Toast.LENGTH_LONG).show();
        super.onResume();
    }
}
