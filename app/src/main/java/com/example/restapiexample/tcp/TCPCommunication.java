package com.example.restapiexample.tcp;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.restapiexample.utils.L;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class TCPCommunication {
    // -----------------------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------------------
    private static final String TAG = TCPCommunication.class.getSimpleName();

    private static final int BUFFER_SEND_SIZE = 81920;
    private static final int BUFFER_RECEIVE_SIZE = 81920;
    private static final int TCP_MTU_SIZE = 1460;
    private static final int SO_TIMEOUT = 20_000; //millis

    // -----------------------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------------------
    private Context context;
    private int remotePort;
    private String remoteAddress;
    private byte[] receiveData;

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private BufferedInputStream bufferedInputStream;

    // -----------------------------------------------------------------------------------
    // Instance creation
    // -----------------------------------------------------------------------------------
    public TCPCommunication(Context context) {
        this.context = context;
        this.remotePort = 8019;
        this.remoteAddress = "10.10.45.76";
        this.receiveData = new byte[BUFFER_RECEIVE_SIZE];
    }

    // -----------------------------------------------------------------------------------
    // Public
    // -----------------------------------------------------------------------------------
    public void initSocket() throws IOException {
        close();
        L.log(TAG, "BIND TCP SOCKET");
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            socket = new Socket(remoteAddress, remotePort);

            socket.setSoTimeout(SO_TIMEOUT);
            socket.setTcpNoDelay(true); //Needed to prevent the femto gluing XML messages together into single packets
            socket.setReuseAddress(true);
            socket.setReceiveBufferSize(BUFFER_RECEIVE_SIZE);
            socket.setSendBufferSize(BUFFER_SEND_SIZE);

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        }
    }

    public String receive() throws Exception {
        try {
            if (socket == null || socket.isClosed()) {
                initSocket();
            }

            int bytesRead, allBytesRead = 0;

            do {
                bytesRead = bufferedInputStream.read(receiveData, allBytesRead, TCP_MTU_SIZE);
                //if not end of stream
                if (bytesRead != -1) {
                    allBytesRead += bytesRead;
                }
            } while (bytesRead == TCP_MTU_SIZE);


            if (allBytesRead == 0) {
                return null;
            } else {
                return new String(receiveData, 0, allBytesRead);
            }
        } catch (SocketException ex) {
            L.log(TAG, "TCP socket not ready yet...");
            return null;
        }
    }

    public void send(String serializedResponse) {
        try {
            byte[] sendData;
            sendData = serializedResponse.getBytes();
            if (socket == null || socket.isClosed()) {
                initSocket();
            }

            dataOutputStream.write(sendData);
        } catch (IOException e) {
            L.log(TAG, "TCP message fail!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            L.log(TAG, "Error while closing the TCP socket");
            e.printStackTrace();
        }
    }
}