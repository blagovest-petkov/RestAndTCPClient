package com.example.restapiexample.rest;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.restapiexample.rest.request.GetStudentsRequest;
import com.example.restapiexample.rest.request.PostStudentsRequest;
import com.example.restapiexample.utils.L;

public class RequestManager {
    // -----------------------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------------------
    private static final String TAG = RequestManager.class.getSimpleName();

    // -----------------------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------------------
    private Context context;
    private RequestQueue queue;

    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------
    public RequestManager(Context context) {
        L.log(TAG, "RequestManager created");
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    // -----------------------------------------------------------------------------------
    // Public
    // -----------------------------------------------------------------------------------
    public void getStudents() {
        L.log(TAG, "GetStudents");
        queue.add(new GetStudentsRequest(context));
    }

    public void postStudent() {
        L.log(TAG, "PostStudent");
        queue.add(new PostStudentsRequest(context));
    }

    public void deleteStudent() {
        /*L.log(TAG, "DeleteStudent");
        queue.add(new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sendResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sendResult(error.getMessage());
                    }
                }));*/
    }
}
