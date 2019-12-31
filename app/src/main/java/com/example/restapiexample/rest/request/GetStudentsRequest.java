package com.example.restapiexample.rest.request;

import android.content.Context;

import com.android.volley.Request;
import com.example.restapiexample.rest.response.GetStudentResponse;

public class GetStudentsRequest extends BaseRequest<GetStudentResponse> {

    private static final String TAG = GetStudentsRequest.class.getSimpleName();

    public GetStudentsRequest(Context context) {
        super(context, Request.Method.GET, URL + "/students", GetStudentResponse.class);
    }

    @Override
    protected void deliverResponse(GetStudentResponse response) {
        sendResult(response.toString());
    }
}
