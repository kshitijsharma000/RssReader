package com.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rssreader.netutils.Appcontroller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kshitij on 5/15/2016.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    private static final String TAG = "News Item Adapter";
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
        Channel.Item item = items.get(position);

        String combinedStr = item.getDescription();
        String url = "";
        Log.d(TAG, combinedStr);
        if (checkImageExists(combinedStr))
            url = combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>')).split("=")[1];

        //"17 May 2016 08:26:31 GMT"
        Date date = null;
        DateFormat formatIn = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss z");
        DateFormat formatOut = new SimpleDateFormat("dd MMMM : hh:mm");
        try {
            date = formatIn.parse(item.getPubDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (url.equals(""))
            holder.mItemImage.setImageResource(R.mipmap.jagran_icon);
        else
            holder.mItemImage.setImageUrl(url, Appcontroller.getmInstance().getImageLoader());
        holder.mItemTitle.setText(item.getTitle());
        holder.mItemDate.setText(formatOut.format(date));
    }

    private boolean checkImageExists(String combinedStr) {
        if (combinedStr.indexOf('<') == -1 || combinedStr.indexOf('>') == -1)
            return false;
        else
            return true;
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
