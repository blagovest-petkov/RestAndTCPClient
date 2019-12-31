package com.example.restapiexample.rest.request;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.restapiexample.rest.response.BaseResponse;
import com.example.restapiexample.utils.BroadcastHelper;
import com.example.restapiexample.utils.Constnats;
import com.example.restapiexample.utils.L;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class BaseRequest<T extends BaseResponse> extends Request<T> {
    // -----------------------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------------------
    private static final String TAG = BaseRequest.class.getSimpleName();
    protected final static Gson gson = new Gson();
    protected static final String URL = "http://10.10.45.76:8080";

    // -----------------------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------------------
    protected Context context;
    private Class responseClass;

    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------
    public BaseRequest(Context context, int method, String url, Class responseClass) {
        super(method, url, null);
        this.context = context;
        this.responseClass = responseClass;
    }

    // -----------------------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------------------
    /**
     * This method needs to be implemented to parse the raw network response
     * and return an appropriate response type.This method will be called from a worker thread.
     * The response will not be delivered if you return null.
     */
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
            L.log(TAG, "RESPONSE SUCCESS: " + jsonString);
            Object result = TextUtils.isEmpty(jsonString) ? new BaseResponse(response.statusCode) : gson.fromJson(jsonString, responseClass);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    /**
     * This is called on the main thread with the object you returned in
     * parseNetworkResponse(). You should be invoking your callback interface from here.
     **/
    @Override
    protected void deliverResponse(T response) {
        // Implement in a child class
    }

    /**
     * This is called on the main thread with the object you returned in
     * parseNetworkError(). You should be invoking your callback interface from here.
     **/
    @Override
    public void deliverError(VolleyError error) {
        String message = "RESPONSE FAIL WITH CODE: " + error.networkResponse.statusCode + " AND MESSAGE " + error.getMessage();
        L.log(TAG, message);
        BroadcastHelper.sendResultToMain(context, message);
    }
}
