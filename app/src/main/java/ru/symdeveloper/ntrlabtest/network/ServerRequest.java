package ru.symdeveloper.ntrlabtest.network;

import com.android.volley.Request;

import ru.symdeveloper.ntrlabtest.Constants;

/**
 * Created by Alexander Fokin on 01.12.2015.
 */
public class ServerRequest {

    private static final String LOG_TAG = "ServerRequest";

    public static Query simpleRequest() {
        return new Query.QueryBuilder()
                .url(Constants.URL_BASE)
                .path(Constants.URL_SIMPLE_REQUEST)
                .id(Constants.REQUEST_SIMPLE)
                .method(Request.Method.GET)
                .build();
    }
}
