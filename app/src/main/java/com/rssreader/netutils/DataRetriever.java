package com.rssreader.netutils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

/**
 * Created by kshitij.sharma on 5/12/2016.
 */
public class DataRetriever {

    private DataListener listener;

    public DataRetriever(DataListener listener) {
        this.listener = listener;
    }

    public void makeRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.dataRecieved(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });
        listener.requestStart();
        Appcontroller.getmInstance().addtoRequestqueue(request);
    }

    public void makeStringRequest(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.dataRecieved(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.error(error);
            }
        });

        listener.requestStart();
        Appcontroller.getmInstance().addtoRequestqueue(request);
    }

    public interface DataListener {

        void requestStart();

        void dataRecieved(JSONObject jsonObject);

        void dataRecieved(String stringObject);

        void error(VolleyError error);
    }
}
