package com.rssreader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
    private NewsAdapter newsAdapter;
    private Channel mChannel;
    private String mUrl;

    public ReaderFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupProgressDialog();
        showDialog();

        newsAdapter = new NewsAdapter(getActivity());
        dataRetriever = new DataRetriever(this);
        mUrl = getArguments().getString("url");
        dataRetriever.makeStringRequest(mUrl);

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
        recyclerView.setAdapter(newsAdapter);
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

    class service extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            mChannel = XmlParser.getParser().parse(params[0]);
            return "Parsing done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            newsAdapter.setItemsList(mChannel.getItems());
            newsAdapter.notifyDataSetChanged();
            Log.d(TAG, s);
            hideDialog();
        }
    }
}
