package ru.symdeveloper.ntrlabtest.managers;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;

import de.greenrobot.event.EventBus;
import ru.symdeveloper.ntrlabtest.events.DataReceivedEvent;
import ru.symdeveloper.ntrlabtest.model.Entity;
import ru.symdeveloper.ntrlabtest.network.LoadObjectRequest;
import ru.symdeveloper.ntrlabtest.network.Query;
import ru.symdeveloper.ntrlabtest.network.ServerRequest;
import ru.symdeveloper.ntrlabtest.network.VolleyBall;
import ru.symdeveloper.ntrlabtest.network.VolleyQueue;

/**
 * Created by Alexander Fokin on 01.12.2015.
 */
public class DataManager {
    private static final String LOG_TAG = "DataManager";

    private static DataManager instance;

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    private VolleyQueue mainRequestQueue;


    public DataManager(Context context) {
        RequestQueue stories_queue = VolleyBall.newStoriesRequestQueue(context);
        mainRequestQueue = new VolleyQueue(stories_queue);
    }

    public void requestData() {
        Query query = ServerRequest.simpleRequest();
        LoadObjectRequest<Entity> request = new LoadObjectRequest<>(query, new Response.Listener<Entity>() {
            @Override
            public void onResponse(Entity response) {
                EventBus.getDefault().post(new DataReceivedEvent(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(LOG_TAG, "send simple request | server error received | message: " + volleyError.getMessage());
                EventBus.getDefault().post(new DataReceivedEvent(volleyError));
            }
        });
        mainRequestQueue.add(request);
    }
}
