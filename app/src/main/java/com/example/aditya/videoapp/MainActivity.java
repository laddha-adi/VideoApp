package com.example.aditya.videoapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
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
    private DatabaseReference mDatabase;
    public TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//loadData();

tv=(TextView)findViewById(R.id.textView5);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


mRecyclerView=(RecyclerView)findViewById(R.id.list_view) ;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // final Button likeBtn=(Button)findViewById(R.id.buttonup);
        // final Button dislikeBtn=(Button)findViewById(R.id.buttondown);
        // final Button finalChoice=(Button)findViewById(R.id.afterclickedBtn);

        getData();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Bollywood");
        mDatabase.keepSynced(true);
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
        FirebaseRecyclerAdapter<mApps,Bollywood.mViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<mApps,Bollywood.mViewHolder>(
                mApps.class,R.layout.list_row,Bollywood.mViewHolder.class,mDatabase
        ){
            @Override
            protected void populateViewHolder(Bollywood.mViewHolder viewHolder, mApps model, int position){
                final String post_key =getRef(position).getKey();
                final String post_add ="Bollywood";
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener( new View.OnClickListener(){
                                                         @Override
                                                         public void onClick(View view) {
                                                             Intent intent=new Intent (MainActivity.this,trialActivity.class);
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
            mView = itemView;
            likeBtn = (Button) mView.findViewById(R.id.buttonup);
            dislikeBtn = (Button) mView.findViewById(R.id.buttondown);
            finalChoice = (Button) mView.findViewById(R.id.afterclickedBtn);
        }

        public void setTitle(String title) {

            TextView PostTitle = (TextView) mView.findViewById(R.id.heading_id);
            PostTitle.setText(title);
        }

        public void setDesc(String desc) {
            //  TextView PostDesc=(TextView)mView.findViewById(R.id.text_id);
            //  PostDesc.setText(desc);
        }

        public void setImage(Context ctx, String Image) {
            final ImageView post_image = (ImageView) mView.findViewById(R.id.image_id);
            Picasso.with(ctx).load(Image).into(post_image);


        }
    }




    public void loadData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        int choice1=pref.getInt("radioChoice",0);

        switch (choice1){
            case 1:  choice1 = 1;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trailer) {
            Intent intent=new Intent (this,Trailer.class);
            startActivity(intent);

        } else if (id == R.id.nav_Hollywood) {
            Intent intent=new Intent (this,Hollywood.class);
            startActivity(intent);

        } else if (id == R.id.nav_Bollywood) {
            Intent intent=new Intent (this,Bollywood.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_TV) {
            Intent intent=new Intent (this,TV.class);
            startActivity(intent);

        }else if (id == R.id.nav_Home) {
            Intent intent=new Intent (this,MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent=new Intent (this,Choice.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
