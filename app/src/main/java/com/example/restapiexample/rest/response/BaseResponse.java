package com.example.restapiexample.rest.response;

import androidx.annotation.NonNull;

public class BaseResponse {
    // Network status
    private int status;

    public BaseResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "Server response with status: " + status;
    }
}
