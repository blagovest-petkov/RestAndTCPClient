package com.example.restapiexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.restapiexample.rest.RequestManager;
import com.example.restapiexample.utils.Constnats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // -----------------------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------------------
    private static final String TAG = MainActivity.class.getSimpleName();

    // -----------------------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------------------
    private TextView tvResult;

    private UpdateResultReceiver updateResultReceiver;


    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------


    public MainActivity() {
        updateResultReceiver= new UpdateResultReceiver();
    }

    // -----------------------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final RequestManager requestManager = new RequestManager(this);

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestManager.getStudents();
            }
        });
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestManager.postStudent();
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestManager.deleteStudent();
            }
        });
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(updateResultReceiver, updateResultReceiver.filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updateResultReceiver);
    }

    // -----------------------------------------------------------------------------------
    // Inner classes
    // -----------------------------------------------------------------------------------
    public class UpdateResultReceiver extends BroadcastReceiver {

        public IntentFilter filter;

        public UpdateResultReceiver() {
            filter = new IntentFilter();
            filter.addAction(Constnats.ACTION_UPDATE_RESULT);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            tvResult.setText(intent.getStringExtra(Constnats.EXTRA_RESULT));
        }

    }
}
