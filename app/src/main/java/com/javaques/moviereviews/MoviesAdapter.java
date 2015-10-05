package com.javaques.moviereviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Anil on 05-Sep-15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private static final String TAG = "MoviesAdapter";
    private final LayoutInflater inflater;
    Context mContext;
    List<Movie> data;
    int previousPosition = -1;
    int websiteId;

    public MoviesAdapter(Context context, int websiteId, List<Movie> data) {
        this.mContext = context;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
        this.websiteId = websiteId;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String poster = null;
        poster = data.get(position).getPoster();
        if (poster == null || poster.length() == 0) {
            Picasso.with(mContext).load(R.drawable.movie_thumb).into(holder.imageView);
        } else {
            Picasso.with(mContext).load(poster).into(holder.imageView);
        }

        //holder.imageView.setImageResource(data.get(position).getPoster());
        holder.textView.setText(data.get(position).getName());

        if (position > previousPosition) {
            AnimationUtil.animate(holder, true); // we are scrolling doun
        } else {
            AnimationUtil.animate(holder, false); // we are scrolling up
        }
        previousPosition = position;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ReviewActivity.class);
                Bundle extras = new Bundle();
                extras.putString("movie_title", data.get(position).getName());
                extras.putString("full_review", data.get(position).getReview());
                extras.putString("link", data.get(position).getLink());
                extras.putInt("website_id", websiteId);
                intent.putExtras(extras);
                mContext.startActivity(intent);
            }
        });


        // setting color
        holder.cover.setBackgroundColor(ContextCompat.getColor(mContext, Util.gridColor()));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        LinearLayout cover;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cover = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
