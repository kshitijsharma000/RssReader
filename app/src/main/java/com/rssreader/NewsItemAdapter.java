package com.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rssreader.netutils.Appcontroller;

import java.util.ArrayList;

/**
 * Created by Kshitij on 5/15/2016.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private ArrayList<Channel.Item> items;

    public NewsItemAdapter(Context context) {
        this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String combinedStr = items.get(position).getDescription();
        String url = combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>')).split("=")[1];

        holder.mItemImage.setImageUrl(url, Appcontroller.getmInstance().getImageLoader());
        holder.mItemTitle.setText(items.get(position).getTitle());
    }

    public void setItemsList(ArrayList<Channel.Item> itemsList) {
        this.items = itemsList;
    }

    public Channel.Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView mItemImage;
        TextView mItemDate;
        TextView mItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemImage = (NetworkImageView) itemView.findViewById(R.id.newsItemThumbnail);
            mItemDate = (TextView) itemView.findViewById(R.id.newsItemDate);
            mItemTitle = (TextView) itemView.findViewById(R.id.newsItemTitle);
        }
    }
}
