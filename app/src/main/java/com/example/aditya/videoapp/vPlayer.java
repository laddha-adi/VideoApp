package com.example.aditya.videoapp;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class vPlayer extends AppCompatActivity {

    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL = "https://firebasestorage.googleapis.com/v0/b/videoapp-6083a.appspot.com/o/Baahubali%202%20-%20The%20Conclusion%20%20Official%20Trailer%20(Hindi)%20%20S.S.%20Rajamouli%20%20Prabhas%20%20Rana%20Daggubati%20-%20YouTube.mp4?alt=media&token=eeeab88d-6c9b-4642-8466-37e93e606947";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.activity_v_player);
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask

        // Create a progressbar
        pDialog = new ProgressDialog(vPlayer.this);
        // Set progressbar title

        pDialog.setTitle("Android Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming TutorialAndroid Video Streaming Tutorial");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    vPlayer.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

    }

}