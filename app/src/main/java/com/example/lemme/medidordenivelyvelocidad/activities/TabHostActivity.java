package com.example.lemme.medidordenivelyvelocidad.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.lemme.medidordenivelyvelocidad.ActivityTabHostType;
import com.example.lemme.medidordenivelyvelocidad.SensorClassFactory;
import com.example.lemme.medidordenivelyvelocidad.R;

public class TabHostActivity extends TabActivity {

    private TabHost tabHost;
    private ActivityTabHostType tabHostType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        getExtrasFromIntent();
        initializeTabHost();
    }

    private void getExtrasFromIntent() {
        Bundle extras = this.getIntent().getExtras();
        tabHostType = (ActivityTabHostType) extras.get("TabHostType");
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
        Intent speedometerIntent = new Intent(this, new SensorClassFactory().create(tabHostType, "speedometerTab"));
        Intent levelMeterIntent = new Intent(this, new SensorClassFactory().create(tabHostType, "levelMeterTab"));
        speedometerTabSpec.setContent(speedometerIntent);
        levelMeterTabSpec.setContent(levelMeterIntent);
        tabHost.addTab(speedometerTabSpec);
        tabHost.addTab(levelMeterTabSpec);
    }
}
