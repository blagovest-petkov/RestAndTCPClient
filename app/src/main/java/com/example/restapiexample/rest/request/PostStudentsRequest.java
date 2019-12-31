package com.example.restapiexample.rest.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.example.restapiexample.rest.data.Student;
import com.example.restapiexample.rest.response.BaseResponse;
import com.example.restapiexample.rest.response.GetStudentResponse;
import com.example.restapiexample.utils.BroadcastHelper;

import java.util.HashMap;
import java.util.Map;

public class PostStudentsRequest extends BaseRequest<BaseResponse> {

    private static final String TAG = PostStudentsRequest.class.getSimpleName();

    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------
    public PostStudentsRequest(Context context) {
        super(context, Method.POST, URL + "/students", BaseResponse.class);
    }

    // -----------------------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------------------
    @Override
    protected void deliverResponse(BaseResponse response) {
        BroadcastHelper.sendResultToMain(context, response.toString());
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        return params;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Student student = new Student(4, "Ivan Ivanov", "Sports");
        return gson.toJson(student).getBytes();
    }
}
