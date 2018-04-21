package com.example.shubham.mytmdb.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubham.mytmdb.Adapters.DescriptionAdapter;
import com.example.shubham.mytmdb.Adapters.trailerAdapter;
import com.example.shubham.mytmdb.R;
import com.example.shubham.mytmdb.Retrofit.ApiClient.ApiClient;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieCredits;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.TrailerClassModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDescriptionActivity extends AppCompatActivity {

    CollapsingToolbarLayout layout;
//    RecyclerView recyclerView;
//    List<MovieCredits.CastBean> List;

    DescriptionAdapter adapter;
    ImageView poster;
    ImageView backdrop;
    TextView description;
    int b;
//    List<TrailerClassModel.ResultsBean> trailerslist;
//    RecyclerView trailerrecycler;
//    trailerAdapter trailerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        poster = findViewById(R.id.tvposter);
        backdrop = findViewById(R.id.tvbackdrop);
        layout = findViewById(R.id.toolbar_layout);
        description = findViewById(R.id.tvdescription);
//        swipeRefreshLayout = findViewById(R.id.descriptionswipe);
        Intent intent = getIntent();
        String a = intent.getStringExtra("moviename");
        b = intent.getIntExtra("movieid", -1);
        String c = intent.getStringExtra("movieposter");
        String d = intent.getStringExtra("moviebackdrop");
        String desc = intent.getStringExtra("description");
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + c).into(this.poster);
        Picasso.get().load("http://image.tmdb.org/t/p/w780" + d).fit().into(this.backdrop);
        description.setText(desc);
        layout.setExpandedTitleGravity(Gravity.RIGHT | Gravity.BOTTOM);
        layout.setExpandedTitleMarginStart(15);
        layout.setTitle(a);
//        createfornowplaying();
//        createForMovieTrailers();
    }

//    private void createForMovieTrailers() {
//        trailerslist = new ArrayList<>();
//        trailerrecycler = findViewById(R.id.recycleryoutube);
//        fetchDataFromYoutube();
//        trailerAdapter = new trailerAdapter(trailerslist, this, new trailerAdapter.ontrailerclickListener() {
//            @Override
//            public void ontrailerClick(int position) {
//                Intent intent = new Intent(MovieDescriptionActivity.this,VideoPlayerActivity.class);
//              TrailerClassModel.ResultsBean bean = trailerslist.get(position);
//              intent.putExtra("video_id",bean.getKey());
//              startActivity(intent);
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                TrailerClassModel.ResultsBean bean = trailerslist.get(position);
//                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + bean.getKey() + "&feature=youtu.be"));
//
//                startActivity(intent);

//                Toast.makeText(MovieDescriptionActivity.this, "HELLO BRo", Toast.LENGTH_SHORT).show();
//            }
//        });
//        trailerrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        trailerrecycler.setAdapter(trailerAdapter);
//        trailerrecycler.setOnFlingListener(null);
//    }

//    private void fetchDataFromYoutube() {
//        Call<TrailerClassModel> tvClassCall = ApiClient.getInstance().getTmdbApiApi().getmovietrailers(b, "4838be8059e2c5739385001c29bd159c", "en-US");
//        tvClassCall.enqueue(new Callback<TrailerClassModel>() {
//            @Override
//            public void onResponse(Call<TrailerClassModel> call, Response<TrailerClassModel> response) {
//                if (response.body() != null) {
//                    TrailerClassModel trailersClass = response.body();
//                    trailerslist.clear();
//                    trailerslist.addAll(trailersClass.getResults());
//                    trailerAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TrailerClassModel> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    private void createfornowplaying() {
//        swipeRefreshLayout.setRefreshing(true);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.colorWhite));
//        List = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclermoviecast);
//        movieDatabase = Room.databaseBuilder(this,MovieDatabase.class,"mymovies").allowMainThreadQueries().build();
//              movieDao=  movieDatabase.getMovieDao();
//              List<Nowplaying.ResultsBean> list =movieDao.getnowplaing();

//        fetchdatafromnetwork();
//        adapter = new DescriptionAdapter(List, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.HORIZONTAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setOnFlingListener(null);
//
//        adapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(false);

//        List.clear();
//        List.addAll(list);
//        adapter.notifyDataSetChanged();
//    }

//    private void fetchdatafromnetwork() {
//
//        Call<MovieCredits> nowplayingCall = ApiClient.getInstance().getTmdbApiApi().getcastMovies(b, "4838be8059e2c5739385001c29bd159c");
//        nowplayingCall.enqueue(new Callback<MovieCredits>() {
//            @Override
//            public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
//                if (response.isSuccessful()) {
//                    MovieCredits root = response.body();
//                    List.clear();
//                    List.addAll(root.getCast());
//                    adapter.notifyDataSetChanged();
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieCredits> call, Throwable t) {
//                Toast.makeText(TvDescriptionActivity.this, "fail to load the activity", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
    }