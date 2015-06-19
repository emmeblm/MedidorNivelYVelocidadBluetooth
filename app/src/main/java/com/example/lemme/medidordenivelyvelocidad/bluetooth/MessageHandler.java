package com.example.lemme.medidordenivelyvelocidad.bluetooth;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.lemme.medidordenivelyvelocidad.chart.Chart;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

/**
 * Created by lemme on 6/19/15.
 */
public class MessageHandler extends Handler {
    private Chart chart;
    private StringBuffer stringBuffer;

    public MessageHandler(final Chart chart) {
        this.chart = chart;
        this.stringBuffer = new StringBuffer();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case Utilities.RECEIVE_MESSAGE:
                byte[] readBuffer = (byte[]) msg.obj;

                stringBuffer.append(new String(readBuffer, 0, msg.arg1));
                int endOfLine = stringBuffer.indexOf("\r\n");
                if(endOfLine > 0){
                    String subString = stringBuffer.substring(0, endOfLine);
                    stringBuffer.delete(0, stringBuffer.length());
                    Log.d(Utilities.TAG, subString);
                    try {
                        chart.getSerie().addSensorLecture(Float.valueOf(subString));
                        chart.updateChart();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
