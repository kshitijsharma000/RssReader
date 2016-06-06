package com.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.rssreader.Model.Channel;
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
        View view;
        if (HostActivity.SUPPORTS_IMAGE)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_no_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Channel.Item item = items.get(position);

        if (HostActivity.SUPPORTS_IMAGE) {
            String url = getImageUrl(item.getDescription());

            if (url == null) {
                switch (HostActivity.mCurrentTab) {
                    case Constants.CHANNEL_NAME_JAGRAN:
                        holder.mItemImage.setImageResource(R.mipmap.jagran_icon);
                        break;
                    case Constants.CHANNEL_NAME_JAGRANJOSH:
                        holder.mItemImage.setImageResource(R.mipmap.jagran_icon);
                        break;
                }
            } else
                holder.mItemImage.setImageUrl(url, Appcontroller.getmInstance().getImageLoader());
        }
        //"17 May 2016 08:26:31 GMT"
        Date date = null;
        DateFormat formatIn = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss z");
        DateFormat formatOut = new SimpleDateFormat("dd MMMM : hh:mm");
        try {
            String temp = item.getPubDate();
            if (temp.contains(","))
                temp = item.getPubDate().split(",")[1];
            date = formatIn.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mItemDate.setText(formatOut.format(date));

        holder.mItemTitle.setText(Html.fromHtml(item.getTitle()));
    }

    private String getImageUrl(String combinedStr) {
        //   Log.d(TAG, combinedStr);
        if (HostActivity.SUPPORTS_IMAGE) {
            if ((combinedStr.indexOf('<') == -1 || combinedStr.indexOf('>') == -1)
                    || combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>')).length() < 2)
                return null;
            else
                return combinedStr.substring(combinedStr.indexOf('<') + 1, combinedStr.indexOf('>')).split("=")[1];
        } else
            return null;
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
            if (HostActivity.SUPPORTS_IMAGE)
                mItemImage = (NetworkImageView) itemView.findViewById(R.id.newsItemThumbnail);
            mItemDate = (TextView) itemView.findViewById(R.id.newsItemDate);
            mItemTitle = (TextView) itemView.findViewById(R.id.newsItemTitle);
        }
    }
}
