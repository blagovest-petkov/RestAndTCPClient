package com.example.restapiexample.tcp;

import android.content.Context;
import android.text.TextUtils;

import com.example.restapiexample.R;
import com.example.restapiexample.utils.BroadcastHelper;
import com.example.restapiexample.utils.L;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient implements Runnable {

    private static final String TAG = TCPClient.class.getSimpleName();

    //------------------------------------------------------------------------------------------------------------------
    // Fields
    //------------------------------------------------------------------------------------------------------------------
    private Context context;
    private String serverIp;
    private int serverPort;

    private PrintWriter out;
    private BufferedReader in;

    private boolean isStarted = false;

    //------------------------------------------------------------------------------------------------------------------
    // Instance creation
    //------------------------------------------------------------------------------------------------------------------
    public TCPClient(Context context, String serverIp, int serverPort) {
        this.context = context;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }


    //------------------------------------------------------------------------------------------------------------------
    // Lifecycle
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
        L.log(TAG, "Connecting...");
        try {
            Socket socket = new Socket(InetAddress.getByName(serverIp), serverPort);

            try {
                // Send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                // Receive the message from the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // In this while the client listens for the messages sent by the server
                while (isStarted) {
                    String receivedMessage = in.readLine();

                    // Check for message to received
                    if (receivedMessage != null) {
                        L.log(TAG, "RECEIVED: " + receivedMessage);
                        BroadcastHelper.sendResultToMain(context, context.getString(R.string.tcp_msg_received, receivedMessage));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // The socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    // Public
    //------------------------------------------------------------------------------------------------------------------
    public void start() {
        isStarted = true;
        new Thread(this).start();
    }

    public void stop() {
        isStarted = false;
    }

    public void sendMessage(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (out != null && !out.checkError() && !TextUtils.isEmpty(message)) {
                    out.println(message);
                    out.flush();
                    L.log(TAG, "SENT: " + message);
                    BroadcastHelper.sendResultToMain(context, context.getString(R.string.tcp_msg_sent, message));
                }
            }
        }).start();
    }
}