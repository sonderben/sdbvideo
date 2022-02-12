package com.sonderben.sdbvideo.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.sonderben.sdbvideo.VideoPlayerActivity;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
//192.168.147.239
    public final static String ip="192.168.0.102";
    //192.168.0.101
    public final static String baseurl="http://"+ip+":8080/api/v1/";
    private final static String baseUrlCountryStateCity="https://api.countrystatecity.in/v1/";
    public static void setEnableViewGroup(View searchView, boolean b) {
        searchView.setEnabled(b);
        if (searchView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) searchView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);

                setEnableViewGroup(child, b);
            }
        }
    }
    public static volatile Retrofit retrofit;
    public static volatile Retrofit retrofit2;



    public static SimpleExoPlayer playVideo(Context context, PlayerView mPlayerView, SimpleExoPlayer mSimpleExoPlayer, String film, String subtitle) {
        Long currentPosition = null;
        if (mSimpleExoPlayer != null) {
            currentPosition = mSimpleExoPlayer.getCurrentPosition();
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
        }


        mSimpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
        Uri uriVideo = Uri.parse(film);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "app"));
        ConcatenatingMediaSource mConcatenatingMediaSource = new ConcatenatingMediaSource();
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(String.valueOf(uriVideo)));


        if (subtitle != null && subtitle.length() > 10) {// verify also if is a valid url, don't forget{
            Format format = Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP, Format.NO_VALUE, "fr");

            MediaSource sourceSubtitle = new SingleSampleMediaSource.Factory(dataSourceFactory)
                    .setTreatLoadErrorsAsEndOfStream(true).createMediaSource(Uri.parse(subtitle), format, C.TIME_UNSET);
            MergingMediaSource mergingMediaSource = new MergingMediaSource(mediaSource, sourceSubtitle);







            mConcatenatingMediaSource.addMediaSource(mergingMediaSource);
        } else {
            mConcatenatingMediaSource.addMediaSource(mediaSource);
        }


        mPlayerView.setPlayer(mSimpleExoPlayer);
        mPlayerView.setKeepScreenOn(true);
        mSimpleExoPlayer.prepare(mConcatenatingMediaSource);
        if (currentPosition != null)
            mSimpleExoPlayer.seekTo(0, currentPosition);
        playError(mSimpleExoPlayer);
        return mSimpleExoPlayer;

    }
    private static void playError(SimpleExoPlayer mSimpleExoPlayer) {
        mSimpleExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                //Toast.makeText(VideoPlayerActivity.this, "error al leer video", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static void setVisibleChildGroup(View searchView, int visibility) {
        //searchView.setEnabled(b);
        if (searchView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) searchView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);

                child.setVisibility(visibility);
            }
        }
    }

    //textTimeToMillis
    public static String timeMillisToTextTime(Long timeInMillis) {
        StringBuilder stringBuilder = new StringBuilder();
        Long sec;
        Long min = 0L;
        Long hour = 0L;

        sec = timeInMillis / 1000;

        while (min > 60 || sec > 60) {

            if (sec > 60) {
                min = sec / 60;
                sec = sec % 60;
            }
            if (min > 60) {
                hour = min / 60;
                min = min % 60;
            }

        }

        if (hour > 0) {
            stringBuilder.append(hour);
        }
        if (min > 0) {
            if (min > 9 && hour > 0)
                stringBuilder.append(":" + min);
            else if (min > 9 && hour == 0)
                stringBuilder.append(min);
            else if (min < 10 && hour > 0) {
                stringBuilder.append(":0" + min);
            } else if (min < 10 && hour == 0) {
                stringBuilder.append("0" + min);
            }
        }
        if (sec > 0) {
            if (sec > 9 && min > 0)
                stringBuilder.append(":" + sec);
            else if (sec > 9 && min == 0)
                stringBuilder.append(sec);
            else if (sec < 10 && min > 0) {
                stringBuilder.append(":0" + sec);
            } else if (sec < 10 && min == 0) {
                stringBuilder.append("0" + sec);
            }
        }


       /* return String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
                TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillis))
        );*/


        return stringBuilder.toString();
    }

    public static void setFullScreen(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
       // if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        /*} /*else {
            return email.trim().isEmpty();
        }*/
    }
    public static boolean isNameValid(String email) {
        if (email == null) {
            return false;
        }
        // if (email.contains("@")) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        /*} /*else {
            return email.trim().isEmpty();
        }*/
    }

    public static Retrofit getInstanceRetrofitCountryStateCity(){
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrlCountryStateCity)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getInstanceRetrofit(){
        if(retrofit2==null) {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }
    public static void signOut(Context context){
        Preferences preferences=Preferences.getPreferenceInstance(context);
        preferences.setTokenPreferences("");
        preferences.setIdProfile(-1L);
    }
    public static Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }
}
