package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.lemme.medidordenivelyvelocidad.R;
import com.example.lemme.medidordenivelyvelocidad.chart.SensorLecture;

import java.util.ArrayList;
import java.util.logging.Level;

import static android.widget.TabHost.*;

public class HistoricalDataActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_data);
        initializeTabHost();
    }

    private void initializeTabHost() {
        tabHost= getTabHost();
        TabHost.TabSpec speedometerTabSpec = tabHost.newTabSpec("speedometerTab");
        speedometerTabSpec.setIndicator("Speedometer");
        Intent speedometerHistoricalIntent = new Intent(this, SpeedometerHistoricalActivity.class);
        speedometerTabSpec.setContent(speedometerHistoricalIntent);

        TabHost.TabSpec levelMeterTabSpec = tabHost.newTabSpec("levelMeterTab");
        levelMeterTabSpec.setIndicator("Level Meter");
        Intent levelMeterHistoricalIntent = new Intent(this, LevelMeterHistoricalActivity.class);
        levelMeterTabSpec.setContent(levelMeterHistoricalIntent);

        tabHost.addTab(speedometerTabSpec);
        tabHost.addTab(levelMeterTabSpec);
    }
}
