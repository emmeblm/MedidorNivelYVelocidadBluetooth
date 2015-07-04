package com.example.lemme.medidordenivelyvelocidad.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lemme on 5/7/15.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private final InputStream readerStream;
    private final OutputStream writerStream;
    private static Handler handler;

    private volatile boolean running = true;

    public ConnectedThread(final BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        InputStream reader = null;
        OutputStream writer = null;
        try {
            reader = this.bluetoothSocket.getInputStream();
            writer = this.bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readerStream = reader;
        writerStream = writer;
    }

    public void initializeHandler(MessageHandler messageHandler) {
        handler = messageHandler;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[256];
        int bytes;
        while(running) {
            try{
                bytes = readerStream.read(buffer);
                handler.obtainMessage(Utilities.RECEIVE_MESSAGE, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                terminate();
            }
        }
    }

    public void write(String message) {
        byte[] messageBuffer = message.getBytes();
        try{
            writerStream.write(messageBuffer);
        } catch (IOException e) {
            Log.e(Utilities.TAG, Utilities.ERROR_SENDING_DATA);
        }
    }

    public void terminate() {
        running = false;
    }

    public InputStream getReaderStream() {
        return readerStream;
    }

    public OutputStream getWriterStream() {
        return writerStream;
    }

    public void setHandler(Handler handler) {
        ConnectedThread.handler = handler;
    }

    public boolean isRunning() {
        return running;
    }
}
