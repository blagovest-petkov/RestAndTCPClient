package com.example.restapiexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restapiexample.rest.RequestManager;
import com.example.restapiexample.tcp.TCPCommunication;
import com.example.restapiexample.tcp.TCPListener;
import com.example.restapiexample.utils.Constnats;
import com.example.restapiexample.utils.L;

public class MainActivity extends AppCompatActivity {
    // -----------------------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------------------
    private static final String TAG = MainActivity.class.getSimpleName();

    // -----------------------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------------------
    private Button btnTcp;
    private TextView tvResult;

    private UpdateResultReceiver updateResultReceiver;
    private TCPCommunication tcpCommunication;
    private TCPListener tcpListener;


    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------


    public MainActivity() {
        updateResultReceiver = new UpdateResultReceiver();
        tcpCommunication = new TCPCommunication(MainActivity.this);
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
        tcpListener = new TCPListener(this);
        //tcpListener.start();

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
        btnTcp = findViewById(R.id.btn_tcp);
        btnTcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendTcpDataTask().execute();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // tcpListener.stop();
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

    public class SendTcpDataTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg) {
            try {
                tcpCommunication.send("One short tcp request!");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSendSuccessful) {
            super.onPostExecute(isSendSuccessful);
            tvResult.setText(isSendSuccessful ? MainActivity.this.getString(R.string.tcp_msg_success) :
                    MainActivity.this.getString(R.string.tcp_msg_fail));
        }

    }
}
