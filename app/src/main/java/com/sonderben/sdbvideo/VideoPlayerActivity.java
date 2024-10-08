package com.sonderben.sdbvideo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.ui.PlayerView;
import com.sonderben.sdbvideo.adapter.AdapterEpo4PlayerView;
import com.sonderben.sdbvideo.adapter.AdapterSubtitle4PlayerView;
import com.sonderben.sdbvideo.data.model.Subtitle;
import com.sonderben.sdbvideo.entity.Episode;

import com.sonderben.sdbvideo.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(this);
        setContentView(R.layout.activity_video_player);
        mCustomPlaybackView = findViewById(R.id.custom_playback_view);
        mPlayerView = findViewById(R.id.player_view);
        mCloseActivity = findViewById(R.id.exo_close);
        mSpeed = findViewById(R.id.speed);
        mLayoutSpeed = findViewById(R.id.layot_speed);
        mCloseSpeed = findViewById(R.id.close_speed);
        mSeekBarSpeed = findViewById(R.id.seekBar);
        mProgressSpeedLabel = findViewById(R.id.progress_speed_label);
        mLayoutLock = findViewById(R.id.layout_lock);
        mLock = findViewById(R.id.lock);
        mUnlock = findViewById(R.id.unlock);
        mEpo = findViewById(R.id.epo);
        mSubtitle = findViewById(R.id.sub);


        urlFilm = getIntent().getStringExtra("URL_FILM");
        subtitles = (ArrayList<Subtitle>) getIntent().getSerializableExtra("LIST_SUBTITLE");
        urlSub = subtitles.get(1).getSubtitle();

        List<MediaItem.SubtitleConfiguration> subtitleConfigurations=new ArrayList<>();
        for(int a=0;a<subtitles.size();a++){
            subtitleConfigurations.add(Utils.createSubtitle(subtitles.get(a).getSubtitle(),subtitles.get(a).getLanguage()));
        }

        mediaItem= new MediaItem.Builder()
                .setUri(urlFilm)
                .setSubtitleConfigurations(subtitleConfigurations)

                .build();

        if (subtitles == null || subtitles.size() == 0)
            mSubtitle.setVisibility(View.GONE);






        mExoplayer = new ExoPlayer.Builder(this).build();
        mPlayerView.setPlayer(mExoplayer);
        mExoplayer.setMediaItem(mediaItem);
        mExoplayer.prepare();
        mExoplayer.play();


        mPlaybackParameters = new PlaybackParameters(1f);
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        mHeightScreen = mDisplayMetrics.heightPixels;
        mWidthScreen = mDisplayMetrics.widthPixels;

        mCloseActivity.setOnClickListener(this::onClick);
        mSpeed.setOnClickListener(this::onClick);
        mLayoutSpeed.setOnClickListener(this::onClick);
        mCloseSpeed.setOnClickListener(this::onClick);
        mLayoutSpeed.setOnFocusChangeListener(this::onFocusChange);
        mLock.setOnClickListener(this::onClick);
        mPlayerView.setOnClickListener(this::onClick);
        mUnlock.setOnClickListener(this::onClick);
        mEpo.setOnClickListener(this::onClick);
        mSubtitle.setOnClickListener(this::onClick);

        setOnSeekBarChangeListener();


    }

    public void setOnSeekBarChangeListener() {
        mSeekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPlaybackParameters = new PlaybackParameters(mSpeedValue[i]);
                mExoplayer.setPlaybackParameters(mPlaybackParameters);
                mProgressSpeedLabel.setText("" + mSpeedValue[i] + "X");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /* private void setFullScreen() {
         requestWindowFeature(Window.FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
     }*/






    @Override
    public void onBackPressed() {
        if (!mScreenIsLock) {
            super.onBackPressed();
            if (mExoplayer.isPlaying())
                mExoplayer.stop();
        } else {
            Toast toast = Toast.makeText(VideoPlayerActivity.this, "You have to unlock the" +
                    " screen first, to do that action.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mExoplayer.setPlayWhenReady(false);
        mExoplayer.getPlaybackState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mExoplayer.setPlayWhenReady(true);
        mExoplayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mExoplayer.setPlayWhenReady(true);
        mExoplayer.getPlaybackState();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.exo_close) {
            mExoplayer.stop();
            mExoplayer.release();
            finish();
        } else if (id == R.id.speed) {
            mLayoutSpeed.setVisibility(View.VISIBLE);
        } else if (id == R.id.close_speed) {
            mLayoutSpeed.setVisibility(View.GONE);
        } else if (id == R.id.lock) {
            mScreenIsLock = true;
            setmUnlockTextView(R.drawable.ic_outline_lock, "", R.drawable.bg_icon_in_cicle);

            mLayoutLock.setVisibility(View.VISIBLE);
            mCustomPlaybackView.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLayoutLock.setVisibility(View.GONE);
                }
            }, 5000);
        } else if (id == R.id.player_view) {

            if (mScreenIsLock) {
                if (mLayoutLock.getVisibility() == View.VISIBLE) {
                    mLayoutLock.setVisibility(View.GONE);
                    //
                    setmUnlockTextView(R.drawable.ic_outline_lock, "", R.drawable.bg_icon_in_cicle);
                    mCanUnlockScreen = false;
                    if (mThread != null)
                        mThread.interrupt();
                } else {
                    mLayoutLock.setVisibility(View.VISIBLE);

                    mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (!Thread.currentThread().isInterrupted()) {
                                try {
                                    Thread.sleep(5000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setmUnlockTextView(R.drawable.ic_outline_lock, "", R.drawable.bg_icon_in_cicle);

                                            mLayoutLock.setVisibility(View.GONE);
                                            mCanUnlockScreen = false;
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    mThread.start();

                }
            }
        } else if (id == R.id.unlock) {

            if (!mCanUnlockScreen) {
                setmUnlockTextView(R.drawable.ic_unlock, "Deverouiller l'ecran ?", R.drawable.bg_icon_in_cicle2);
                mCanUnlockScreen = true;
            } else {
                mLayoutLock.setVisibility(View.GONE);
                mCustomPlaybackView.setVisibility(View.VISIBLE);
                mScreenIsLock = false;
                mCanUnlockScreen = false;
            }
        } else if (id == R.id.epo) {

            episodeDialog().show();
        } else if (id == R.id.sub) {
            AlertDialog a = subtitleDialog();
            a.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    //mExoplayer = Utils.playVideo(VideoPlayerActivity.this,  mPlayerView, mExoplayer,  urlFilm,  urlSub);
                   // mediaItem.set
                }
            });
            a.show();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id) {
            case R.id.layot_speed:
                if (b)
                    mLayoutSpeed.setVisibility(View.GONE);
                break;
        }
    }

    public void setmUnlockTextView(int idDrawable, String text, int bgDrawable) {
        mUnlock.setCompoundDrawablesWithIntrinsicBounds(idDrawable, 0, 0, 0);
        mUnlock.setText(text);
        mUnlock.setBackgroundResource(bgDrawable);
    }

    public AlertDialog episodeDialog() {
        mExoplayer.pause();
        mCustomPlaybackView.setVisibility(View.INVISIBLE);
        AlertDialog.Builder dialodAddIncome = new AlertDialog.Builder(VideoPlayerActivity.this);
        AlertDialog alertDialog = dialodAddIncome.create();

        LayoutInflater inflater = (LayoutInflater) VideoPlayerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_of_episode, null);
        alertDialog.setView(view);
        //alertDialog.show();
        alertDialog.getWindow().setLayout((int) (mWidthScreen * 0.95f), (int) (mHeightScreen * 0.90f));
        alertDialog.setOnDismissListener(dialogInterface -> {
            mCustomPlaybackView.setVisibility(View.VISIBLE);
            mExoplayer.play();
        });

        view.findViewById(R.id.close).setOnClickListener(x -> {
            alertDialog.cancel();
        });

        List<Episode> episodes = new ArrayList<>();
        int dura = 10_000_000;


        for (int a = 0; a < 10; a++)
            episodes.add(new Episode("Alger pleur", "https://ejemploht.s3.us-east-2.amazonaws.com/alger+pleur.jpg",
                    a + 1, dura, (long) (dura * 2),
                    "Il est un des rares officiers français qui a reconnu sur le tard l'utilisation de la torture tout en la justifiant. La publication de son livre"));


        mRecyclerViewEpo = view.findViewById(R.id.recyclerview_epo);
        mRecyclerViewEpo.setLayoutManager(new LinearLayoutManager(VideoPlayerActivity.this, LinearLayoutManager.HORIZONTAL, false));

        AdapterEpo4PlayerView adapter = new AdapterEpo4PlayerView(episodes);
        mRecyclerViewEpo.setAdapter(adapter);
        return alertDialog;
    }

    public AlertDialog subtitleDialog() {
        mExoplayer.pause();
        mCustomPlaybackView.setVisibility(View.INVISIBLE);
        AlertDialog.Builder dialodAddIncome = new AlertDialog.Builder(VideoPlayerActivity.this);
        AlertDialog alertDialog = dialodAddIncome.create();

        LayoutInflater inflater = (LayoutInflater) VideoPlayerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_of_subtitle, null);
        alertDialog.setView(view);
        alertDialog.getWindow().setLayout((int) (mWidthScreen * 0.95f), (int) (mHeightScreen * 0.90f));
        alertDialog.setOnDismissListener(dialogInterface -> {
            mCustomPlaybackView.setVisibility(View.VISIBLE);
            mExoplayer.play();
        });

        view.findViewById(R.id.close).setOnClickListener(x -> {
            alertDialog.cancel();
        });


        mListViewSubtitle = view.findViewById(R.id.listview_subtitle);

        AdapterSubtitle4PlayerView adapter = new AdapterSubtitle4PlayerView(subtitles, VideoPlayerActivity.this);
        mListViewSubtitle.getSelectedItemPosition();
        mListViewSubtitle.setAdapter(adapter);
        return alertDialog;
    }

    Thread mThread;
    ExoPlayer mExoplayer;
    PlayerView mPlayerView;
    MediaItem mediaItem;

    ImageView mCloseActivity, mCloseSpeed;
    TextView mSpeed, mProgressSpeedLabel, mLock, mUnlock, mEpo, mSubtitle;
    ConstraintLayout mLayoutSpeed;
    PlaybackParameters mPlaybackParameters;
    SeekBar mSeekBarSpeed;
    LinearLayout mLayoutLock;
    ConstraintLayout mCustomPlaybackView;
    RecyclerView mRecyclerViewEpo;
    ListView mListViewSubtitle;
    boolean mCanUnlockScreen = false;
    boolean mScreenIsLock = false;
    float mSpeedValue[] = {0.50f, 0.75f, 1.00f, 1.25f, 1.50f};
    DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    int mHeightScreen;
    int mWidthScreen;
    ArrayList<Subtitle> subtitles;
    String urlFilm, urlSub;

}
