package com.example.aditya.videoapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class fullActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView myText;
    public Button mButton;
    private FirebaseRemoteConfig remoteConfig=FirebaseRemoteConfig.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

mButton=(Button)findViewById(R.id.buttonplay);
        setContentView(R.layout.activity_full);
        String data = getIntent().getExtras().getString("key");
        myText=(TextView)findViewById(R.id.myText);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog").child(data);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (fullActivity.this,trialActivity.class);

                startActivity(intent);
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myText.setText(dataSnapshot.child("desc").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

