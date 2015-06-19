package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.lemme.medidordenivelyvelocidad.R;
import com.example.lemme.medidordenivelyvelocidad.bluetooth.ConnectedThread;
import com.example.lemme.medidordenivelyvelocidad.chart.Chart;
import com.example.lemme.medidordenivelyvelocidad.commons.Utilities;

import java.util.HashMap;

public class LevelMeterActivity extends Activity {
    Chart levelChart;
    private ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_meter);
        initializeChart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createDataStreamToTalkToTheServer();
        connectedThread.write("L");
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
        connectedThread = new ConnectedThread(Utilities.bluetoothSocket, levelChart);
        connectedThread.initializeHandler();
        connectedThread.start();
        Log.d(Utilities.TAG, "... Listening ... ");
    }

    private void initializeChart() {
        HashMap<String, Object> serieOptions = new HashMap<>();
        serieOptions.put("Name", getString(R.string.level_serie_name));
        serieOptions.put("Line Color", getResources().getColor(R.color.green));
        serieOptions.put("Point Color", getResources().getColor(R.color.darkGreen));
        serieOptions.put("Fill Color", getResources().getColor(R.color.transparentGreen));
        serieOptions.put("Sampling Step", Utilities.SENSOR_SAMPLING_STEP);
        serieOptions.put("Min Y-Axis Value", Utilities.MIN_Y_AXIS_VALUE_LEVEL_METER);
        serieOptions.put("Max Y-Axis Value", Utilities.MAX_Y_AXIS_VALUE_LEVEL_METER);

        levelChart = new Chart(findViewById(R.id.chartLevel), serieOptions);
        levelChart.setDefaultSerieFormat();
        levelChart.initializeSerie();
        levelChart.updateChart();
    }
}
