package com.example.aditya.videoapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import static com.example.aditya.videoapp.R.id.videoView;

public class Player extends AppCompatActivity {
    public Button playButton;
    public Button pauseButton;
    public TextView textView;

    public int old_duration=0;
    public  Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playButton = (Button) findViewById(R.id.play);
        pauseButton = (Button) findViewById(R.id.pause);
        textView= (TextView)findViewById(R.id.textView2);
        textView.setVisibility(View.INVISIBLE);
        final VideoView buckysVideoView = (VideoView)findViewById(videoView);
        buckysVideoView.setVideoPath("https://firebasestorage.googleapis.com/v0/b/videoapp-6083a.appspot.com/o/2.%20Android%20-%20MySQL%20-%2002%20-%20Add%20data%20into%20MySQL%20Database%20-%20YouTube.mp4?alt=media&token=be0b6d97-342c-4a4f-bb61-cc738ab38ce2");

        //Player controls(play, pause, stop, etc...)
        MediaController mediaController = new MediaController(this) {
            @Override
            public void hide() {
            }
        };

        mediaController.setAnchorView(buckysVideoView);
        buckysVideoView.setMediaController(mediaController);
        buckysVideoView.start();

      /*  buckysVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                           bufferingStarted();
                        }
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                            bufferingStopped();
                        return false;
                    }
                });
            }
        });
*/
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buckysVideoView.stopPlayback();
            }

        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buckysVideoView.pause();

            }
        });
        final Handler handler = new Handler();
        runnable = new Runnable()
        {
            public void run() {
                int duration = buckysVideoView.getCurrentPosition();
                if ( buckysVideoView.isPlaying()&& old_duration==duration) {
                    pauseButton.setText("äsdfghjk");
                    textView.setVisibility(View.VISIBLE);


                } else {
                    pauseButton.setText("abe teri to");
                    textView.setVisibility(View.INVISIBLE);
                }
                old_duration=duration;
                handler.postDelayed(runnable,1);
            }
        };
        handler.postDelayed(runnable, 0);


    }

    private void bufferingStarted() {
    }
    private void bufferingStopped() {
    }

}