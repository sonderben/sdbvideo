package com.sonderben.sdbvideo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.sonderben.sdbvideo.R;
import com.sonderben.sdbvideo.entity.Episode;
import com.sonderben.sdbvideo.entity.Subtitle;
import com.sonderben.sdbvideo.utils.Utils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterSubtitle4PlayerView extends BaseAdapter {

    private List<Subtitle>subtitles;
    private Context context;
    private View view;
    ConcatenatingMediaSource concatenatingMediaSource;
    public View getView(){
        return view;
    }
    public AdapterSubtitle4PlayerView(List<Subtitle>subtitles,Context context,ConcatenatingMediaSource concatenatingMediaSource){
        this.concatenatingMediaSource=concatenatingMediaSource;
        this.subtitles=subtitles;
        this.context=context;
    }

    @Override
    public int getCount() {
        return subtitles.size();
    }

    @Override
    public Object getItem(int i) {
        return subtitles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
        view =inflater.inflate(R.layout.custum_view_subtitle,viewGroup,false);
        this.view=view;
        TextView textViewSubtitle= view.findViewById(R.id.subtitle);
        ImageView imgViewCheck= view.findViewById(R.id.checked);
        textViewSubtitle.setText(subtitles.get(i).getLang());
        view.setOnClickListener(x->{
            // change subtitle
            //concatenatingMediaSource.removeMediaSource(0);


            Toast.makeText(context,"subtitle clicked",Toast.LENGTH_LONG).show();

        });
        return view;
    }
}
