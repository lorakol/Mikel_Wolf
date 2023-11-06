package com.training.mediaplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener  {
    private MediaPlayerService mediaPlayerService;
    private boolean isBound = false;
    private SwitchCompat mToggleLoop;
    private SwitchCompat mToggleShuffle;
    private ImageButton mButtonPlay;
    private ImageButton mButtonPause;
    private ImageButton mButtonStop;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonNext;
    private ImageView mImageViewAlbumArt;
    private SeekBar mSeekBar;
    private Handler mHandler = new Handler();
    private TextView mTextViewSongTitle;
    private int nPlayFlag = 0; // 0: stop, 1: play, 2: pause


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToggleLoop = findViewById(R.id.toggle_loop);
        mToggleShuffle = findViewById(R.id.toggle_shuffle);
        mButtonPlay = findViewById(R.id.btn_play);
        mButtonStop = findViewById(R.id.btn_stop);
        mButtonPrevious = findViewById(R.id.btn_previous);
        mButtonNext = findViewById(R.id.btn_next);
        mImageViewAlbumArt = findViewById(R.id.img_album_art);
        mSeekBar = findViewById(R.id.seek_bar);
        mTextViewSongTitle = findViewById(R.id.text_song_title);

        mToggleLoop.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isBound) {
                mediaPlayerService.setLooping(isChecked);
            }
        });
        mToggleShuffle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isBound) {
                mediaPlayerService.setShuffling(isChecked);
            }
        });
        mButtonPlay.setOnClickListener(v -> {
            if (isBound) {
                if(nPlayFlag != 1){
                    mButtonPlay.setImageResource(R.drawable.pause);
                    nPlayFlag = 1;
                    mTextViewSongTitle.setText(mediaPlayerService.getCurrentTitle());
                    mediaPlayerService.play();
                    new Thread(mUpdateSeekBarRunnable).start();
                }
                else{
                    mButtonPlay.setImageResource(R.drawable.play);
                    nPlayFlag = 2;
                    mediaPlayerService.pause();
                }

            }
        });



        mButtonStop.setOnClickListener(v -> {
            if (isBound) {
                mButtonPlay.setImageResource(R.drawable.play);
                nPlayFlag = 0;
                mediaPlayerService.stop();
            }
        });

        mButtonPrevious.setOnClickListener(v -> {
            if (isBound) {
                mediaPlayerService.skipPrevious();
            }
        });

        mButtonNext.setOnClickListener(v -> {
            if (isBound) {
                mediaPlayerService.skipNext();
            }
        });

        mSeekBar.setOnSeekBarChangeListener(this);


    }



    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MediaPlayerService.MediaPlayerBinder binder = (MediaPlayerService.MediaPlayerBinder) service;
            mediaPlayerService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b && isBound) {
            mediaPlayerService.seekTo(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private Runnable mUpdateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            if (isBound && mSeekBar != null) {
                int currentPosition = mediaPlayerService.getCurrentPosition();
                int duration = mediaPlayerService.getDuration();
                int nprogress = currentPosition * 100 / duration;
                mSeekBar.setProgress(nprogress);
                mHandler.postDelayed(this, 1000);
                mTextViewSongTitle.setText(mediaPlayerService.getCurrentTitle());
                if(mediaPlayerService.isPlaying() && nPlayFlag != 1){
                    nPlayFlag = 1;
                    mButtonPlay.setImageResource(R.drawable.pause);
                }
                if(!mediaPlayerService.isPlaying() && nPlayFlag == 1){
                    if(nprogress == 0 || nprogress == 100){
                        nPlayFlag = 0;
                    }
                    else{
                        nPlayFlag = 2;
                    }
                    mButtonPlay.setImageResource(R.drawable.play);
                }
            }
        }
    };

    private void updateSeekBar() {
        mHandler.removeCallbacks(mUpdateSeekBarRunnable);
        mHandler.postDelayed(mUpdateSeekBarRunnable, 1000);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}
