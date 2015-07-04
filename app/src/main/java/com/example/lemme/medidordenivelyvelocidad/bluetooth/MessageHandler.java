package com.example.lemme.medidordenivelyvelocidad.bluetooth;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.lemme.medidordenivelyvelocidad.chart.Chart;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

import java.util.ArrayList;

/**
 * Created by lemme on 6/19/15.
 */
public class MessageHandler extends Handler {
    private Chart chart;
    private StringBuffer rxBuffer;
    private float acceptedErrorRange;

    public MessageHandler(final Chart chart) {
        this.chart = chart;
        this.rxBuffer = new StringBuffer();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Utilities.RECEIVE_MESSAGE:
                byte[] readBuffer = (byte[]) msg.obj;
                rxBuffer.append(new String(readBuffer, 0, msg.arg1));
                int endOfLine = rxBuffer.indexOf("\r\n");
                if(endOfLine > 0){
                    String receivedMessage = validateReceivedMessage(endOfLine);
                    rxBuffer.delete(0, rxBuffer.length());
                    if(receivedMessage != null) {
                        Log.d(Utilities.TAG, receivedMessage);
                        try {
                            chart.getDataSerie().addSensorLecture(Float.valueOf(receivedMessage));
                            chart.updateChart();
                            wait(1000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private String validateReceivedMessage(int endOfLine) {
        ArrayList<Float> serie = chart.getDataSerie().getSerie();
        Float actualLecture;
        String acceptedLecture = null;
        try {
            actualLecture = Float.valueOf(rxBuffer.substring(0, endOfLine));
            acceptedLecture = getAcceptedLecture(serie, actualLecture).toString();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return acceptedLecture;
    }

    private Float getAcceptedLecture(ArrayList<Float> serie, Float actualLecture) {
        Float lastLecture = 1f;
        if (serie.size() > 0) {
            acceptedErrorRange = lastLecture * 5;
            lastLecture = serie.get(serie.size() - 1);
            if (actualLecture <= lastLecture + acceptedErrorRange && lastLecture >= lastLecture - acceptedErrorRange) {
                return actualLecture;
            }
            return lastLecture;
        }
        return actualLecture;
    }
}
