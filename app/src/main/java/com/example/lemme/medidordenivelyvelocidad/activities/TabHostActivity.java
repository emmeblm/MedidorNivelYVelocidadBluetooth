package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.example.lemme.medidordenivelyvelocidad.R;

public class TabHostActivity extends TabActivity {
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        initializeTabHost();
    }

    private void initializeTabHost() {
        tabHost= getTabHost();
        initializeTabSpecs();
    }

    private void initializeTabSpecs() {
        TabHost.TabSpec speedometerTabSpec = tabHost.newTabSpec("speedometerTab");
        TabHost.TabSpec levelMeterTabSpec = tabHost.newTabSpec("levelMeterTab");
        speedometerTabSpec.setIndicator("Medidor de Velocidad");
        levelMeterTabSpec.setIndicator("Medidor de Nivel");
        Intent speedometerIntent = new Intent(this, SpeedometerHistoricalActivity.class);
        Intent levelMeterIntent = new Intent(this, LevelMeterHistoricalActivity.class);
        speedometerTabSpec.setContent(speedometerIntent);
        levelMeterTabSpec.setContent(levelMeterIntent);
        tabHost.addTab(speedometerTabSpec);
        tabHost.addTab(levelMeterTabSpec);
    }
}
