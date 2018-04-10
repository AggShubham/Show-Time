package com.example.shubham.mytmdb;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3;
    ProgressBar progressBar, progressBar1, progressBar2, progressBar3;
    NSMovieRecyclerAdapter recyclerAdapter;
    ArrayList<movie.ResultsBean> movies;
    ArrayList<movie.ResultsBean> popmovies;
    ArrayList<movie.ResultsBean> topmovies;
    ArrayList<movie.ResultsBean> upcomingmovies;
    movieDAO moviedao;
    MovieDatabase movieDatabase;
    MovieSelectedCallback mCallback;
    String type;

    public interface MovieSelectedCallback {
        void onMovieSelected(movie.ResultsBean movie);
    }


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MovieSelectedCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity should implement UserSelectedCallback");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        recyclerView = view.findViewById(R.id.movielist);
        recyclerView1 = view.findViewById(R.id.popularmovielist);
        recyclerView2 = view.findViewById(R.id.topmovielist);
        recyclerView3 = view.findViewById(R.id.upcomingmovielist);
        progressBar = view.findViewById(R.id.progress);
        progressBar1 = view.findViewById(R.id.progress1);
        progressBar2 = view.findViewById(R.id.progress2);
        progressBar3 = view.findViewById(R.id.progress3);
        movies = new ArrayList<>();
        popmovies = new ArrayList<>();
        topmovies = new ArrayList<>();
        upcomingmovies = new ArrayList<>();

        movieDatabase = MovieDatabase.getINSTANCE(getContext());
        moviedao = movieDatabase.getmovieDAO();
        type = "Upcoming";
        upcomingmovies = (ArrayList<movie.ResultsBean>) moviedao.getAllMovies(type);
        Toast.makeText(getContext(), "dao is running", Toast.LENGTH_SHORT).show();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

        fetchNowPlaying();
        recyclerAdapter = new NSMovieRecyclerAdapter(getContext(), movies, new NSMovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                movie.ResultsBean movie = movies.get(position);
                mCallback.onMovieSelected(movie);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        fetchPopular();
        recyclerAdapter = new NSMovieRecyclerAdapter(getContext(), popmovies, new NSMovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                movie.ResultsBean movie = popmovies.get(position);
                mCallback.onMovieSelected(movie);

            }
        });
        recyclerView1.setAdapter(recyclerAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        fetchTopRated();
        recyclerAdapter = new NSMovieRecyclerAdapter(getContext(), topmovies, new NSMovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                movie.ResultsBean movie = topmovies.get(position);
                mCallback.onMovieSelected(movie);
            }
        });
        recyclerView2.setAdapter(recyclerAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        fetchUpcomingMovies();
        recyclerAdapter = new NSMovieRecyclerAdapter(getContext(), upcomingmovies, new NSMovieRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                movie.ResultsBean movie = upcomingmovies.get(position);
                mCallback.onMovieSelected(movie);
            }
        });
        recyclerView3.setAdapter(recyclerAdapter);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView3.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

//    public void onMovieSelected(movie.ResultsBean movie){
//
//    }

    private void fetchUpcomingMovies() {
        recyclerView2.setVisibility(View.GONE);
        progressBar3.setVisibility(View.VISIBLE);

        String apikey = "4838be8059e2c5739385001c29bd159c";
        Call<movie> call = ApiClient.getInstance().getTmdbApiApi().getupcomingmovies(apikey);
        call.enqueue(new Callback<movie>() {

            @Override
            public void onResponse(Call<movie> call, Response<movie> response) {
                movie movieslist = response.body();
                int size = movieslist.getResults().size();
                for (int i = 0; i < size; i++) {
                    movie.ResultsBean resultsBean = movieslist.getResults().get(i);
                    resultsBean.setType("Upcoming");
                }
                Log.d("movie", movieslist + "");

                if (movieslist != null) {
                    upcomingmovies.clear();
                    upcomingmovies.addAll(movieslist.getResults());
                    Log.d("Message", movieslist.getResults().size() + "");
                    recyclerAdapter.notifyDataSetChanged();
                    for (movie.ResultsBean resultsBean : movieslist.getResults())
                        moviedao.insertMovie(resultsBean);
                } else {
                    Toast.makeText(getContext(), "no movies fetched", Toast.LENGTH_SHORT).show();
                }
                recyclerView3.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<movie> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView3.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.GONE);
            }
        });
    }

    private void fetchTopRated() {
        recyclerView2.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);

        String apikey = "4838be8059e2c5739385001c29bd159c";
        Call<movie> call = ApiClient.getInstance().getTmdbApiApi().gettopratedmovies(apikey);
        call.enqueue(new Callback<movie>() {

            @Override
            public void onResponse(Call<movie> call, Response<movie> response) {
                movie movieslist = response.body();
                int size = movieslist.getResults().size();
                for (int i = 0; i < size; i++) {
                    movie.ResultsBean resultsBean = movieslist.getResults().get(i);
                    resultsBean.setType("Top Rated");
                }
                Log.d("movie", movieslist + "");

                if (movieslist != null) {
                    topmovies.clear();
                    topmovies.addAll(movieslist.getResults());

                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "no movies fetched", Toast.LENGTH_SHORT).show();
                }
                recyclerView2.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<movie> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView2.setVisibility(View.VISIBLE);
                progressBar2.setVisibility(View.GONE);
            }
        });
    }

    private void fetchPopular() {
        recyclerView1.setVisibility(View.GONE);
        progressBar1.setVisibility(View.VISIBLE);

        String apikey = "4838be8059e2c5739385001c29bd159c";
        Call<movie> call = ApiClient.getInstance().getTmdbApiApi().getpopmovies(apikey);
        call.enqueue(new Callback<movie>() {

            @Override
            public void onResponse(Call<movie> call, Response<movie> response) {
                movie movieslist = response.body();
                int size = movieslist.getResults().size();
                for (int i = 0; i < size; i++) {
                    movie.ResultsBean resultsBean = movieslist.getResults().get(i);
                    resultsBean.setType("Popular");
                }
                Log.d("movie", movieslist + "");

                if (movieslist != null) {
                    popmovies.clear();
                    popmovies.addAll(movieslist.getResults());
                    Log.d("Message", movieslist.getResults().size() + "");
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "no movies fetched", Toast.LENGTH_SHORT).show();
                }
                progressBar1.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<movie> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView1.setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.GONE);
            }
        });
    }

//
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
//                @Override
//                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                    int from = viewHolder.getAdapterPosition();
//                    int to = target.getAdapterPosition();
//                    Collections.swap(followers,from,to);
//                    recyclerAdapter.notifyItemMoved(from,to);
//                    return true;
//                }
//
//                @Override
//                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//
//                    int position = viewHolder.getAdapterPosition();
//                    followers.remove(position);
//                    recyclerAdapter.notifyItemRemoved(position);
//
//                }
//            });
//            itemTouchHelper.attachToRecyclerView(recyclerView);

    private void fetchNowPlaying() {

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        String apikey = "4838be8059e2c5739385001c29bd159c";
        Call<movie> call = ApiClient.getInstance().getTmdbApiApi().getmovies(apikey);
        call.enqueue(new Callback<movie>() {

            @Override
            public void onResponse(Call<movie> call, Response<movie> response) {
                movie movieslist = response.body();
                int size = movieslist.getResults().size();
                for (int i = 0; i < size; i++) {
                    movie.ResultsBean resultsBean = movieslist.getResults().get(i);
                    resultsBean.setType("Now Playing");
                }
                Log.d("movie", movieslist + "");

                if (movieslist != null) {
                    movies.clear();
                    movies.addAll(movieslist.getResults());
                    Log.d("Message", movieslist.getResults().size() + "");
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "no movies fetched", Toast.LENGTH_SHORT).show();
                }
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<movie> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });


    }
}
