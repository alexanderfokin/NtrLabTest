package ru.symdeveloper.ntrlabtest.network;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

public class RequestUtils {
    public static Cache.Entry createEntry(NetworkResponse response, int ttlPerSecond) {
        if(ttlPerSecond == 0)
            return null;

        Cache.Entry cache = HttpHeaderParser.parseCacheHeaders(response);
        cache.softTtl = System.currentTimeMillis() + ttlPerSecond * 1000;
        cache.ttl = cache.softTtl;
        return cache;
    }
}
