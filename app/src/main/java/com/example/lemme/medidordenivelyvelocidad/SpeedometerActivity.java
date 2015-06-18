package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class SpeedometerActivity extends Activity {
    Chart speedChart;
    private ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);
        initializeChart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createDataStreamToTalkToTheServer();
        connectedThread.write("S");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(connectedThread != null) {
            connectedThread.write("0");
            connectedThread.terminate();
        }
    }

    private void createDataStreamToTalkToTheServer() {
        connectedThread = new ConnectedThread(Utilities.bluetoothSocket, speedChart);
        connectedThread.initializeHandler();
        connectedThread.start();
        Log.d(Utilities.TAG, "... Listening ... ");
    }

    private void initializeChart() {
        HashMap<String, Object> serieOptions = new HashMap<>();
        serieOptions.put("Name", getString(R.string.speed_serie_name));
        serieOptions.put("Line Color", getResources().getColor(R.color.redBluetoothDot));
        serieOptions.put("Point Color", getResources().getColor(R.color.darkRedBluetoothDot));
        serieOptions.put("Fill Color", getResources().getColor(R.color.transparentRedBluetoothDot));
        serieOptions.put("Sampling Step", Utilities.SENSOR_SAMPLING_STEP);
        serieOptions.put("Min Y-Axis Value", Utilities.MIN_Y_AXIS_VALUE_SPEEDOMETER);
        serieOptions.put("Max Y-Axis Value", Utilities.MAX_Y_AXIS_VALUE_SPEEDOMETER);

        speedChart = new Chart(findViewById(R.id.chartSpeed), serieOptions);
        speedChart.setDefaultSerieFormat();
        speedChart.initializeSerie();
        speedChart.updateChart();
    }
}
