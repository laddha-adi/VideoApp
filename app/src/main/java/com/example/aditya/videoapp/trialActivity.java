package com.example.aditya.videoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static com.example.aditya.videoapp.R.id.videoView;

public class trialActivity extends List {
public int seq=0;
public String url;
    public String choiceData;
    public String datasp;
        public int old_duration=0;
        public  Runnable runnable;
    private DatabaseReference mDatabase;
    private DatabaseReference titleDatabase;
    private DatabaseReference likesDatabase;
    private DatabaseReference choiceDatabase;
    private DatabaseReference descDatabase;
public TextView titleView;
public TextView descView;
    public String ChoiceData;
public Button Likebtn;
public Button DLikebtn;
    public TextView textView;
    public Boolean once=false;
    VideoView mVideoView;
    TextView buffer;
    int random=0;
    String[] tokens;
    SharedPreferences sharedpreferences;
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trial);
            final String data = getIntent().getExtras().getString("key");
            final String add = getIntent().getExtras().getString("add");
            sharedpreferences = getSharedPreferences("keyPref", Context.MODE_PRIVATE);
            int y=sharedpreferences.getInt("keys", 0);
String z=Integer.toString(y);
buffer=(TextView)findViewById(R.id.Buffer);
            textView= (TextView)findViewById(R.id.textView2);
            titleView= (TextView)findViewById(R.id.Title);

            descView= (TextView)findViewById(R.id.desc);

          Likebtn =(Button)findViewById(R.id.like);
          DLikebtn =(Button)findViewById(R.id.Dlike);


            textView.setVisibility(View.INVISIBLE);
            final VideoView mVideoView = (VideoView) findViewById(videoView);




            choiceDatabase= FirebaseDatabase.getInstance().getReference().child(z);
            mDatabase= FirebaseDatabase.getInstance().getReference().child(add).child(data).child("url");
            titleDatabase= FirebaseDatabase.getInstance().getReference().child(add).child(data).child("title");
            descDatabase= FirebaseDatabase.getInstance().getReference().child(add).child(data).child("desc");

            MediaController mediaController = new MediaController(this) {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    super.onConfigurationChanged(newConfig);

                    System.out.println("IN onConfigurationChanged()");
                }
                @Override
                public void hide() {
                }
            };
            mVideoView.setMediaController(new MediaController(this){
                public boolean dispatchKeyEvent(KeyEvent event)
                {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
                        ((Activity) getContext()).finish();

                    return super.dispatchKeyEvent(event);
                }
            });
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mVideoView.setVideoPath(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            titleDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    titleView.setText(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            descDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    descView.setText(dataSnapshot.getValue().toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            choiceDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                   ChoiceData=dataSnapshot.getValue().toString();
                  //String str = ChoiceData;
                    tokens = ChoiceData.split("&&", 0);

                    //ArrayList<String> sList = (ArrayList<String>) Arrays.asList(str.split("^^"));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
          /*  dlikesDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mVideoView.setVideoPath(dataSnapshot.getValue().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mVideoView.start();


            final Handler handler = new Handler();
            runnable = new Runnable()
            {
                public void run() {
                    int duration = mVideoView.getCurrentPosition();
                    if ( mVideoView.isPlaying()&& old_duration==duration) {

                        textView.setVisibility(View.VISIBLE);
                        buffer.setVisibility(View.VISIBLE);
                        Random rand = new Random();

                        int  random = rand.nextInt(5) + 1;
textView.setText(tokens[random]);

                    } else {
                        textView.setVisibility(View.INVISIBLE);
                        buffer.setVisibility(View.INVISIBLE);
                    }
                    old_duration=duration;
                    handler.postDelayed(runnable,200);
                }
            };
            handler.postDelayed(runnable, 0);
        }

    @Override
    public void onBackPressed() {
        mVideoView.pause();
        Intent intent=new Intent (this,MainActivity.class);
        startActivity(intent);
        System.exit(0);
super.onBackPressed();
        super.finish();
    }
    private void countDown() {
        new CountDownTimer(50000, 1000) {

            public void onTick(long millisUntilFinished) {

                textView.setText(tokens[i]);
                //textView.setText(tokens[1]);
            }

            public void onFinish() {
                i++;

                textView.setVisibility(View.INVISIBLE);
            once=false;
            }
        }.start();

    }


public int i=0;

}
