package com.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.rssreader.netutils.DataRetriever;

import org.json.JSONObject;

/**
 * Created by Kshitij on 5/13/2016.
 */
public class ReaderFragment extends Fragment implements DataRetriever.DataListener {

    private static final String TAG = "Reader Fragment";
    private RecyclerView recyclerView;
    private DataRetriever dataRetriever;
    private ProgressDialog progressDialog;
    private NewsItemAdapter newsItemAdapter;
    private Channel mChannel;
    private String mUrl;

    public ReaderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setupProgressDialog();
        //showDialog();

        getActivity().setProgressBarIndeterminateVisibility(true);

        newsItemAdapter = new NewsItemAdapter(getActivity());
        dataRetriever = new DataRetriever(this);
        mUrl = getArguments().getString("url");
        dataRetriever.makeStringRequest(mUrl);

        getActivity().setProgressBarIndeterminateVisibility(true);
        getActivity().setProgressBarVisibility(true);

        View view = inflater.inflate(R.layout.reader_fragment, container, false);
        return view;
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

    private void packSendItem(int pos) {
        Channel.Item mItem = newsItemAdapter.getItem(pos);
        Intent intent = new Intent(getActivity(), NewsItemDetailActivity.class);
        intent.putExtra("title", mItem.getTitle());
        intent.putExtra("desc", mItem.getDescription());
        intent.putExtra("date", mItem.getPubDate());
        intent.putExtra("link", mItem.getLink());
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading Data...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void showDialog() {
        progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.hide();
    }

    @Override
    public void requestStart() {
        Log.d(TAG, "News Item request Started : " + mUrl);
    }

    @Override
    public void dataRecieved(JSONObject jsonObject) {
        //Not used as of now..
    }

    @Override
    public void dataRecieved(final String stringObject) {
        new service().execute(stringObject);
    }

    @Override
    public void error(VolleyError error) {
        Log.d(TAG, "Volley Error : " + error.toString());
    }

    interface Clicklistener {
        public void Onclick(View view, int position);

        public void OnLongclick(View view, int position);
    }

    class service extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            mChannel = XmlParser.getParser().parse(params[0]);
            return "Parsing done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            newsItemAdapter.setItemsList(mChannel.getItems());
            newsItemAdapter.notifyDataSetChanged();
            Log.d(TAG, s);

            getActivity().setProgressBarIndeterminateVisibility(false);
            getActivity().setProgressBarVisibility(false);
            //hideDialog();
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
