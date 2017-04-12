package com.example.aditya.videoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Trailer extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private DatabaseReference jokeDatabase;
    private DatabaseReference newsDatabase;
    private DatabaseReference funFactsDatabase;
    private DatabaseReference aboutMoviesDatabase;
    SharedPreferences jokesSP;
    SharedPreferences funFactsSP;
    SharedPreferences newsSP;
    SharedPreferences aboutMoviesSP;
    public String push;
    public String Message1;
    public String Message2;
    public String Message3;
    public String Message4;
    public String Message5;

    private Button likeBtn;
    private Button dislikeBtn;
    private Button finalChoice;
    public int choice=0;
    SharedPreferences sharedpreferen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bollywood);

        mRecyclerView=(RecyclerView)findViewById(R.id.list_view) ;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // final Button likeBtn=(Button)findViewById(R.id.buttonup);
        // final Button dislikeBtn=(Button)findViewById(R.id.buttondown);
        // final Button finalChoice=(Button)findViewById(R.id.afterclickedBtn);

        getData();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Trailer");
        mDatabase.keepSynced(true);


       /* jokeDatabase.child("joke1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 push=dataSnapshot.getValue().toString();
getData();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/

    }

    private void getData() {
        sharedpreferen = getSharedPreferences("keyPref", Context.MODE_PRIVATE);
        int y=sharedpreferen.getInt("keys", 0);
        switch(y){
            case 1: jokeData();
                break;
            case 2: funFactsData();
                break;
            case 3: newsData();
                break;
            case 4: aboutMoviesData();
                break;
            default:
                break;

        }
    }

    private void jokeData() {
        //SharedPreferences.Editor editor = jokesSP.edit();
        jokeDatabase= FirebaseDatabase.getInstance().getReference().child("jokes");
        jokeDatabase.child("joke1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message1=dataSnapshot.getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // String one=jokeDatabase.child("joke1").toString();
       /*        editor.remove("jokes");
        editor.putString("jokes", push);

        editor.commit();
*/
    }
    private void funFactsData() {
        funFactsDatabase=FirebaseDatabase.getInstance().getReference().child("funFacts");
        funFactsDatabase.keepSynced(true);
    }
    private void newsData() {
        newsDatabase=FirebaseDatabase.getInstance().getReference().child("newsData");
        newsDatabase.keepSynced(true);
    }
    private void aboutMoviesData() {
        aboutMoviesDatabase=FirebaseDatabase.getInstance().getReference().child("aboutMovies");
        aboutMoviesDatabase.keepSynced(true);
    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<mApps,mViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<mApps,mViewHolder>(
                mApps.class,R.layout.list_row,mViewHolder.class,mDatabase
        ){
            @Override
            protected void populateViewHolder(mViewHolder viewHolder, mApps model, int position){
                final String post_key =getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                final String post_add ="Trailer";
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener( new View.OnClickListener(){
                                                         @Override
                                                         public void onClick(View view) {
                                                             Intent intent=new Intent (Trailer.this,trialActivity.class);
                                                             intent.putExtra("key",post_key);
                                                             intent.putExtra("add",post_add);
                                                             startActivity(intent);
                                                             //Toast.makeText(List.this, post_key , Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                );


                viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        likeBtn.setVisibility(View.INVISIBLE);
                        dislikeBtn.setVisibility(View.INVISIBLE);
                        finalChoice.setBackgroundResource(R.drawable.blue);
                        finalChoice.setVisibility(View.VISIBLE);
                        choice=1;
                    }
                });
                viewHolder.dislikeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        likeBtn.setVisibility(View.INVISIBLE);
                        dislikeBtn.setVisibility(View.INVISIBLE);
                        finalChoice.setVisibility(View.VISIBLE);
                        finalChoice.setBackgroundResource(R.drawable.pink);
                        choice=2;
                    }
                });
                viewHolder.finalChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finalChoice.setVisibility(View.INVISIBLE);
                        if(choice==1){
                            finalChoice.setBackgroundResource(R.drawable.up1);
                        }
                        if(choice==2){
                            finalChoice.setBackgroundResource(R.drawable.down1);
                        }
                    }
                });

            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class mViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button likeBtn;
        Button dislikeBtn;
        Button finalChoice;

        public mViewHolder(View itemView) {

            super(itemView);
            mView=itemView;
            likeBtn=(Button)mView.findViewById(R.id.buttonup);
            dislikeBtn=(Button)mView.findViewById(R.id.buttondown);
            finalChoice=(Button)mView.findViewById(R.id.afterclickedBtn);
        }
        public void setTitle(String title){

            TextView PostTitle=(TextView)mView.findViewById(R.id.heading_id);
            PostTitle.setText(title);
        }
        public void setDesc(String desc){
           // TextView PostDesc=(TextView)mView.findViewById(R.id.text_id);
           // PostDesc.setText(desc);
        }
        public void setImage(Context ctx, String Image){
            final ImageView post_image= (ImageView)mView.findViewById(R.id.image_id);
            Picasso.with(ctx).load(Image).into(post_image);


        }


    }


}
