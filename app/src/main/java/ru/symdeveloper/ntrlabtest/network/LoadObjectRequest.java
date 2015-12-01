package ru.symdeveloper.ntrlabtest.network;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.symdeveloper.ntrlabtest.Constants;
import ru.symdeveloper.ntrlabtest.model.Entity;
import ru.symdeveloper.ntrlabtest.utils.Utils;

/**
 * Created by Alexander Fokin (af@voltmobi.com) on 08.04.2015.
 */

public class LoadObjectRequest<T> extends Request {
    private static final String LOG_TAG = "LoadObjectRequest";
    Query query;
    Response.Listener<T> success;

    public LoadObjectRequest(Query query, Response.Listener<T> success, Response.ErrorListener listener) {
        super(query.method, query.url, listener);
        this.success = success;
        this.query = query;
        Log.d(LOG_TAG, "url: " + query.url + " | id: " + query.id);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        T result = null;
        try {
            String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            try {
                JSONObject object = new JSONObject(data);
                JSONObject errorObject = object.getJSONObject("error");
                if (errorObject != null) {
                    Log.e(LOG_TAG, "Server error | url: " + query.url + " | message: " + errorObject.toString() + " | " + String.valueOf(response.statusCode));
                    VolleyError error = new BaseServerError(errorObject);
                    return Response.error(error);
                }

            } catch (Exception ignored) {
            }

            result = handleResponse(data);

        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(e));
        }

        Cache.Entry cache = RequestUtils.createEntry(response, query.ttl);

        return Response.success(result, cache);
    }

    @SuppressWarnings ("unchecked")
    private T handleResponse(String data) {
        switch (query.id) {
            case Constants.REQUEST_SIMPLE:
                return (T) simpleResponseHandle(data);
        }
        return null;
    }

    private Entity simpleResponseHandle(String data) {
        Log.d(LOG_TAG, "simpleResponseHandle | data: " + data);
        return Utils.getGson().fromJson(data, Entity.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        return headers;
    }

    protected Map<String, String> getParams() {
        return query.params;
    }

    @Override
    @SuppressWarnings ("unchecked")
    protected void deliverResponse(Object response) {
        if (this.success != null) {
            this.success.onResponse((T) response);
        }
    }
}
