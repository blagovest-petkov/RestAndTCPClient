package com.example.restapiexample.rest.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.example.restapiexample.rest.data.Student;
import com.example.restapiexample.rest.response.BaseResponse;
import com.example.restapiexample.rest.response.GetStudentResponse;

import java.util.HashMap;
import java.util.Map;

public class PostStudentsRequest extends BaseRequest<BaseResponse> {

    private static final String TAG = PostStudentsRequest.class.getSimpleName();

    public PostStudentsRequest(Context context) {
        super(context, Method.POST, URL + "/students", BaseResponse.class);
    }

    @Override
    protected void deliverResponse(BaseResponse response) {
        sendResult(response.toString());
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
