package ru.symdeveloper.ntrlabtest.network;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class BaseServerError extends VolleyError {
    protected JSONObject mErrorObject;

    public BaseServerError(JSONObject errorObject) {
        super(errorObject.toString());
        this.mErrorObject = errorObject;
    }

    public JSONObject errorObject() {
        return mErrorObject;
    }

}
