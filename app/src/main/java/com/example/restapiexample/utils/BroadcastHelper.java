package com.example.restapiexample.utils;

import android.content.Context;
import android.content.Intent;

public class BroadcastHelper {
    public static void sendResultToMain(Context context, String result) {
        Intent intent = new Intent(Constnats.ACTION_UPDATE_RESULT);
        intent.putExtra(Constnats.EXTRA_RESULT, result);
        context.sendBroadcast(intent);
    }
}
