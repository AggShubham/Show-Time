package com.example.shubham.mytmdb.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieModel;
import com.example.shubham.mytmdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shubham on 24-03-2018.
 */

public class NSMovieRecyclerAdapter extends RecyclerView.Adapter<NSMovieRecyclerAdapter.UserViewHolder> {
    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<MovieModel.ResultsBean> movies;
    Context context;
    OnItemClickListener listener;

    public NSMovieRecyclerAdapter(Context context, ArrayList<MovieModel.ResultsBean> movies, OnItemClickListener listener){
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.movie_tab,parent,false);
        UserViewHolder holder = new UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        MovieModel.ResultsBean movie = movies.get(position);
        holder.title.setText(movie.getOriginal_title());
        holder.vote.setText( movie.getVote_average()+"");
        Picasso.get().load("http://image.tmdb.org/t/p/w780"+movie.getBackdrop_path())
                .into(holder.movie_poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView movie_poster;
        TextView vote;
        TextView genre;
        View itemView;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.textView);
            movie_poster = itemView.findViewById(R.id.imageView);
            vote = itemView.findViewById(R.id.textView2);
        }
    }
}
