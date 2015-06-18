package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lemme.medidordenivelyvelocidad.R;
import com.example.lemme.medidordenivelyvelocidad.chart.SensorLecture;

import java.util.ArrayList;

public class LevelMeterHistoricalActivity extends Activity {
    private ListView listViewMediciones;
    private ArrayList<String> lectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_meter_historical);
        listViewMediciones = (ListView) this.findViewById(R.id.listViewLevelMeter);
        ArrayAdapter<String> adaptador = crearAdaptadorListaMediciones();
        listViewMediciones.setAdapter(adaptador);
    }

    private ArrayAdapter<String> crearAdaptadorListaMediciones() {
        ArrayList<SensorLecture> sensorLectures = new ArrayList<>();
        sensorLectures.addAll(SensorLecture.listAll(SensorLecture.class));
        lectures = new ArrayList<>();
        for(SensorLecture sensorLecture : sensorLectures) {
            lectures.add(String.valueOf(sensorLecture.getSensorLecture()));
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lectures);
        return adaptador;
    }

}
