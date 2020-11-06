package com.example.interfacerecipeasy.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

// https://developer.android.com/training/volley/requestqueue#singleton

public class MarthaQueue {
    private static MarthaQueue instance;
    private Context context;
    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private MarthaQueue(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static MarthaQueue getInstance(Context context) {
        if (instance == null) {
            instance = new MarthaQueue(context);
        }
        return instance;
    }

    public <T> void send(Request<T> request) {
        getRequestQueue().add(request);
    }
}
