package ru.symdeveloper.ntrlabtest.network;

import com.android.volley.Request;

public interface Queue {
	public void add(Request<?> request);
}
