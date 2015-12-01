package ru.symdeveloper.ntrlabtest.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

import ru.symdeveloper.ntrlabtest.Constants;
import ru.symdeveloper.ntrlabtest.utils.Utils;

public class VolleyBall {
    /** Default on-disk cache directory. */
    private static final String DEFAULT_CACHE_DIR = "volley";

    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
    private static final int DEFAULT_MAX_CACHE_SIZE_IN_BYTES = 128 * 1024 * 1024;

    /**
     * Creates a default instance of the worker pool and calls {@link com.android.volley.RequestQueue#start()} on it.
     *
     * @param context A {@link android.content.Context} to use for creating the cache dir.
     * @return A started {@link com.android.volley.RequestQueue} instance.
     */
    private static RequestQueue newRequestQueue(Context context, int maxCacheSizeInBytes, int threadPoolSize) {
        File cacheDir = new File(Utils.getDiskCacheDir(context, Constants.CACHE_DIR), DEFAULT_CACHE_DIR);

        String userAgent = "ru.symdeveloper.ntrlabtest/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        HttpStack stack = new HurlStack();

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir, maxCacheSizeInBytes), network, threadPoolSize);
        queue.start();

        return queue;
    }

    /**
     * Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
     *
     * @param context A {@link Context} to use for creating the cache dir.
     * @return A started {@link RequestQueue} instance.
     */
    private static RequestQueue newRequestQueue(Context context, int threadPoolSize) {
        return newRequestQueue(context, DEFAULT_MAX_CACHE_SIZE_IN_BYTES, threadPoolSize);
    }

    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, DEFAULT_NETWORK_THREAD_POOL_SIZE);
    }

    public static RequestQueue newStoriesRequestQueue(Context context) {
        return newRequestQueue(context, 1);
    }

}
