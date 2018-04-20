package com.example.shubham.mytmdb.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shubham.mytmdb.R;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.TrailerClassModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shubham on 07-04-2018.
 */
public class trailerAdapter extends RecyclerView.Adapter<trailerAdapter.TrailerHolder> {

    List<TrailerClassModel.ResultsBean> list;
    Context context;
    ontrailerclickListener listener;
    public static final String YOUTUBE_THUMBNAIL ="https://img.youtube.com/vi/";

    public interface ontrailerclickListener{
        void ontrailerClick(int position);
    }

    public trailerAdapter(List<TrailerClassModel.ResultsBean> list, Context context, ontrailerclickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.trailer_layout,parent,false);
        TrailerHolder holder = new TrailerHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, final int position) {
        TrailerClassModel.ResultsBean resultsBean =  list.get(position);
        holder.name.setText(resultsBean.getName());
        Picasso.get().load(YOUTUBE_THUMBNAIL+ resultsBean.getKey()+"/0.jpg").fit().into(holder.imageView);
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.ontrailerClick(holder.getAdapterPosition());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View itemView;
        ImageView imageView;
        TextView name;
        public TrailerHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            imageView = itemView.findViewById(R.id.trailerimages);
            name = itemView.findViewById(R.id.trailername);
            itemView.setOnClickListener(this);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.ontrailerClick(getAdapterPosition());
        }
    }

}
