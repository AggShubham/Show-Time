package com.example.shubham.mytmdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class movieDescription extends AppCompatActivity {

    CollapsingToolbarLayout layout;
    RecyclerView recyclerView;
    List<MovieCredits.CastBean>List;

    DescriptionAdapter adapter;
    ImageView poster;
    ImageView backdrop;
    TextView description;
    int b;
    List<TrailerClass.ResultsBean> trailerslist;
    RecyclerView trailerrecycler;
    trailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        poster = findViewById(R.id.poster);
        backdrop = findViewById(R.id.backdrop);
        layout = findViewById(R.id.toolbar_layout);
        description = findViewById(R.id.description);
//        swipeRefreshLayout = findViewById(R.id.descriptionswipe);
        Intent intent = getIntent();
        String a = intent.getStringExtra("moviename");
        b = intent.getIntExtra("movieid",-1);
        String c = intent.getStringExtra("movieposter");
        String d =  intent.getStringExtra("moviebackdrop");
        String desc =intent.getStringExtra("description");
        Picasso.get().load("http://image.tmdb.org/t/p/w780"+c).into(this.poster);
        Picasso.get().load("http://image.tmdb.org/t/p/w780"+d).fit().into(this.backdrop);
        description.setText(desc);
        layout.setExpandedTitleGravity(Gravity.RIGHT|Gravity.BOTTOM);
        layout.setExpandedTitleMarginStart(15);
        layout.setTitle(a);
        createfornowplaying();
        createformovietrailers();
    }

    private void createformovietrailers() {
        trailerslist = new ArrayList<>();
        trailerrecycler = findViewById(R.id.recycleryoutube);
        fetchdatafromyoutube();
        trailerAdapter = new trailerAdapter(trailerslist, this, new trailerAdapter.ontrailerclickListener() {
            @Override
            public void ontrailerClick(int position) {

            }
        });
        trailerrecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        trailerrecycler.setAdapter(trailerAdapter);
        trailerrecycler.setOnFlingListener(null);
    }

    private void fetchdatafromyoutube() {
        Call<TrailerClass> tvClassCall = ApiClient.getInstance().getTmdbApiApi().getmovietrailers(b,"4838be8059e2c5739385001c29bd159c","en-US");
        tvClassCall.enqueue(new Callback<TrailerClass>() {
            @Override
            public void onResponse(Call<TrailerClass> call, Response<TrailerClass> response) {
                if(response.body()!=null) {
                    TrailerClass trailersClass = response.body();
                    trailerslist.clear();
                    trailerslist.addAll(trailersClass.getResults());
                    trailerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TrailerClass> call, Throwable t) {

            }
        });
    }


    private void createfornowplaying() {
//        swipeRefreshLayout.setRefreshing(true);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.colorWhite));
        List = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclermoviecast);
//        movieDatabase = Room.databaseBuilder(this,MovieDatabase.class,"mymovies").allowMainThreadQueries().build();
//              movieDao=  movieDatabase.getMovieDao();
//              List<Nowplaying.ResultsBean> list =movieDao.getnowplaing();

        fetchdatafromnetwork();
        adapter =  new DescriptionAdapter(List,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnFlingListener(null);

        adapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(false);

//        List.clear();
//        List.addAll(list);
//        adapter.notifyDataSetChanged();
    }

    private void fetchdatafromnetwork() {

        Call<MovieCredits> nowplayingCall = ApiClient.getInstance().getTmdbApiApi().getcastMovies(b,"4838be8059e2c5739385001c29bd159c");
        nowplayingCall.enqueue(new Callback<MovieCredits>() {
            @Override
            public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                if (response.isSuccessful()) {
                    MovieCredits root = response.body();
                    List.clear();
                    List.addAll(root.getCast());
                    adapter.notifyDataSetChanged();

                }

            }
            @Override
            public void onFailure(Call<MovieCredits> call, Throwable t) {
                Toast.makeText(movieDescription.this, "fail to load the activity", Toast.LENGTH_SHORT).show();
            }
        });


    }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
}
