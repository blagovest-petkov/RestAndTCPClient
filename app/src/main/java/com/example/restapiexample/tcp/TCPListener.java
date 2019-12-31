package com.example.restapiexample.tcp;

import android.content.Context;

import com.example.restapiexample.utils.BroadcastHelper;
import com.example.restapiexample.utils.L;

public class TCPListener implements Runnable {
    //------------------------------------------------------------------------------------------------------------------
    // Constant
    //------------------------------------------------------------------------------------------------------------------
    private static final String TAG = TCPListener.class.getSimpleName();

    //------------------------------------------------------------------------------------------------------------------
    // Fields
    //------------------------------------------------------------------------------------------------------------------
    private Context context;
    private TCPCommunication communication;
    private Thread thread;

    //------------------------------------------------------------------------------------------------------------------
    // Instance creation
    //------------------------------------------------------------------------------------------------------------------

    public TCPListener(Context context) {
        this.context = context;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {

        L.log(TAG, "Runnable started");
        while (true) {
            try {
                BroadcastHelper.sendResultToMain(context, communication.receive());
            } catch (Exception e) {
                L.log(TAG, communication.getClass().getSimpleName() + " Listening exception ");
                e.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // Public
    //------------------------------------------------------------------------------------------------------------------
    public void start() {
        communication = new TCPCommunication(context);
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        communication.close();
    }
}
