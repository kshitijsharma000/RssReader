package com.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.rssreader.Model.Channel;
import com.rssreader.db.FeedReaderDBController;
import com.rssreader.netutils.DataRetriever;
import com.rssreader.utils.XmlParser;

import org.json.JSONObject;

/**
 * Created by Kshitij on 5/13/2016.
 */
public class ReaderFragment extends Fragment implements DataRetriever.DataListener {

    private static final String TAG = "Reader Fragment";
    private RecyclerView recyclerView;

    private NewsItemAdapter newsItemAdapter;
    private Channel mChannel;
    private String mChannelType;
    private String mUrl;
    private FeedReaderDBController dbController;
    private DataRetriever dataRetriever;
    private boolean updateDB;
    private UpdateListener updateListener;

    public ReaderFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        updateListener = ((UpdateListener) activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mChannelType = getArguments().getString("channeltype");
        mUrl = getArguments().getString("url");
        Log.d(TAG, "Channel type received is : " + mChannelType);

        if (updateListener != null)
            updateListener.showProgressBar();

        dataRetriever = new DataRetriever(this);

        newsItemAdapter = new NewsItemAdapter(getActivity());
        dbController = new FeedReaderDBController(getActivity().getApplicationContext());

        if (checkInternetConnection())
            dataRetriever.makeStringRequest(mUrl);
        else
            new service().execute(mChannelType);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reader_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleSpaceDecorator(5, 10));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsItemAdapter);

        recyclerView.addOnItemTouchListener(new DragController(getActivity(), new Clicklistener() {
            @Override
            public void Onclick(View view, int position) {
                System.out.println("inside fragment on click : " + position);
                packSendItem(position);
            }

            @Override
            public void OnLongclick(View view, int position) {
                System.out.println("inside fragment on long click : " + position);
            }
        }));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        updateListener = null;
    }

    private void packSendItem(int pos) {
        Channel.Item mItem = newsItemAdapter.getItem(pos);
        Intent intent = new Intent(getActivity(), NewsItemDetailActivity.class);
        intent.putExtra("title", mItem.getTitle());
        intent.putExtra("desc", mItem.getDescription());
        intent.putExtra("date", mItem.getPubDate());
        intent.putExtra("link", mItem.getLink());
        startActivity(intent);
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "Internet is conencted..");
            return true;
        }
        Log.d(TAG, "No Internet");
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "reader fragment on options selected");
        switch (item.getItemId()) {
            case R.id.menu_sync:
                if (checkInternetConnection()) {
                    dataRetriever.makeStringRequest(mUrl);
                    updateDB = true;
                } else {
                    Snackbar.make(recyclerView, "Oops!! Check Internet", Snackbar.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void requestStart() {
        Log.d(TAG, "Volley Request Started.. " + mChannelType);
    }

    @Override
    public void dataRecieved(JSONObject jsonObject) {

    }

    @Override
    public void dataRecieved(String stringObject) {
        mChannel = XmlParser.getParser().parse(stringObject);

        if (updateDB) {
            dbController.insertChanneltoDB(mChannel, mChannelType);
            updateDB = false;
        }
        newsItemAdapter.setItemsList(mChannel.getItems());
        newsItemAdapter.notifyDataSetChanged();

        if (updateListener != null)
            updateListener.hideProgressBar();
    }

    @Override
    public void error(VolleyError error) {
        Log.d(TAG, "Volley Error..");
    }


    interface Clicklistener {
        public void Onclick(View view, int position);

        public void OnLongclick(View view, int position);
    }

    interface UpdateListener {
        void showProgressBar();

        void hideProgressBar();
    }

    class service extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            mChannel = dbController.readChannelFromDB(mChannelType);
            Log.d(TAG, "items read from Db : " + mChannel.getItems().size());
            return "Parsing done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            newsItemAdapter.setItemsList(mChannel.getItems());
            newsItemAdapter.notifyDataSetChanged();

            Log.d(TAG, s);
            if (updateListener != null)
                updateListener.hideProgressBar();
        }
    }

    private class DragController implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private Clicklistener clicklistener;

        public DragController(Context context, final Clicklistener clicklistener) {
            this.clicklistener = clicklistener;
            this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    //return super.onSingleTapUp(e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.OnLongclick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.Onclick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
