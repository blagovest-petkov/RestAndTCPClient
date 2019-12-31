package com.example.restapiexample.rest.request;

import android.content.Context;

import com.android.volley.Request;
import com.example.restapiexample.rest.response.GetStudentResponse;
import com.example.restapiexample.utils.BroadcastHelper;

public class GetStudentsRequest extends BaseRequest<GetStudentResponse> {

    private static final String TAG = GetStudentsRequest.class.getSimpleName();

    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------
    public GetStudentsRequest(Context context) {
        super(context, Request.Method.GET, URL + "/students", GetStudentResponse.class);
    }

    // -----------------------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------------------
    @Override
    protected void deliverResponse(GetStudentResponse response) {
        BroadcastHelper.sendResultToMain(context, response.toString());
    }
}
