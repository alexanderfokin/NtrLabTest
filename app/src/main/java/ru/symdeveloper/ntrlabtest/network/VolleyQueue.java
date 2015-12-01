package ru.symdeveloper.ntrlabtest.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

public class VolleyQueue implements Queue {
    private RequestQueue mRequestQueue;
    public VolleyQueue(RequestQueue requestQueue){
        mRequestQueue = requestQueue;
    }

    @Override
    public void add(Request<?> request) {
        mRequestQueue.add(request);
    }

    public void cancelAllRequests() {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
}
