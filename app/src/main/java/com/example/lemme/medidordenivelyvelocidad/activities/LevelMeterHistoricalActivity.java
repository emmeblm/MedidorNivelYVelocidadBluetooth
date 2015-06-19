package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lemme.medidordenivelyvelocidad.R;
import com.example.lemme.medidordenivelyvelocidad.array.adapter.CustomArrayAdapter;
import com.example.lemme.medidordenivelyvelocidad.chart.SensorLecture;

import java.util.ArrayList;

public class LevelMeterHistoricalActivity extends Activity {
    private ListView listViewLectures;
    private ArrayList<SensorLecture> sensorLectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_meter_historical);
        initializeListView();
    }

    private void initializeListView() {
        listViewLectures = (ListView) this.findViewById(R.id.listViewLevelMeter);
        CustomArrayAdapter sensorLectureArrayAdapter = createAdapterSensorLectures();
        listViewLectures.setAdapter(sensorLectureArrayAdapter);
    }

    private CustomArrayAdapter createAdapterSensorLectures() {
        sensorLectures = new ArrayList<>();
        sensorLectures.addAll(
                SensorLecture.find(
                        SensorLecture.class,
                        "sensor_name like ?",
                        getString(R.string.level_serie_name)));
        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this, sensorLectures);
        return customArrayAdapter;
    }
}
