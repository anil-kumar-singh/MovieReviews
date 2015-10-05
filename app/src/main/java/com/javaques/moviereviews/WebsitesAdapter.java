package com.javaques.moviereviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anil on 01-Sep-15.
 */
public class WebsitesAdapter extends RecyclerView.Adapter<WebsitesAdapter.MyViewHolder> {
    Context mContext;
    List<Website> websites;
    LayoutInflater inflater;
    int previousPosition = -1;

    public WebsitesAdapter(Context mContext, List<Website> websites) {
        this.mContext = mContext;
        this.websites = websites;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.website_list_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        myViewHolder.imageView.setImageResource(websites.get(position).getLogo());
        myViewHolder.textView.setText(websites.get(position).getTitle());

        if (position > previousPosition) {
            AnimationUtil.animate(myViewHolder, true); // we are scrolling doun
        } else {
            AnimationUtil.animate(myViewHolder, false); // we are scrolling up
        }
        previousPosition = position;
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MoviesActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("website_id", websites.get(position).getId());
                extras.putString("website_name", websites.get(position).getTitle());
                extras.putString("feed_url", websites.get(position).getUrl());
                intent.putExtras(extras);
                mContext.startActivity(intent);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return websites.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
