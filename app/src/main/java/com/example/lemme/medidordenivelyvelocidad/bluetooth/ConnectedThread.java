package com.example.lemme.medidordenivelyvelocidad.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.lemme.medidordenivelyvelocidad.chart.Chart;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by lemme on 5/7/15.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private Chart chart;
    private StringBuffer stringBuffer;
    private final InputStream readerStream;
    private final OutputStream writerStream;
    private static Handler handler;

    private volatile boolean running = true;

    public ConnectedThread(final BluetoothSocket bluetoothSocket, final Chart chart) {
        this.bluetoothSocket = bluetoothSocket;
        this.chart = chart;
        stringBuffer = new StringBuffer();
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

    public void initializeHandler() {
        handler = new MessageHandler()

//                new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case Utilities.RECEIVE_MESSAGE:
//                        byte[] readBuffer = (byte[]) msg.obj;
//
//                        stringBuffer.append(new String(readBuffer, 0, msg.arg1));
//                        int endOfLine = stringBuffer.indexOf("\r\n");
//                        if(endOfLine > 0){
//                            String subString = stringBuffer.substring(0, endOfLine);
//                            stringBuffer.delete(0, stringBuffer.length());
//                            Log.d(Utilities.TAG, subString);
//                            try {
//                                chart.getSerie().addSensorLecture(Float.valueOf(subString));
//                                chart.updateChart();
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
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
